package com.sonic.music.data.connect

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.sonic.music.model.Track
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class SonicConnectManager(
    private val context: Context,
    private val coroutineScope: CoroutineScope
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("sonic_connect_prefs", Context.MODE_PRIVATE)

    private val client = SonicConnectClient()

    // Host configuration
    private val _serverHost = MutableStateFlow(loadInitialHost())
    val serverHost: StateFlow<String> = _serverHost.asStateFlow()

    val port: Int = 3300

    // Unique device identity
    val deviceId: String = loadOrCreateDeviceId()
    val deviceName: String = "Android Phone (${Build.MODEL})"

    // Reactive State
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _devices = MutableStateFlow<List<ConnectDevice>>(emptyList())
    val devices: StateFlow<List<ConnectDevice>> = _devices.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // Command listener callback invoked when PC/laptop sends control commands
    var onCommandReceived: ((ConnectCommandDto) -> Unit)? = null

    // Background jobs
    private var heartbeatJob: Job? = null
    private var pollJob: Job? = null

    // Cached current playback status to include in heartbeat
    private var cachedTrack: Track? = null
    private var cachedIsPlaying: Boolean = false
    private var cachedPositionSeconds: Double = 0.0

    init {
        startHeartbeatLoop()
        startPollLoop()
        refreshDevices()
    }

    private fun loadInitialHost(): String {
        val saved = prefs.getString("server_host", null)
        if (!saved.isNullOrBlank()) return saved
        // If running in Android emulator (generic/goldfish), 10.0.2.2 reaches PC host
        val isEmulator = Build.FINGERPRINT.startsWith("generic") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86")
        return if (isEmulator) "10.0.2.2" else "192.168.29.163"
    }

    private fun loadOrCreateDeviceId(): String {
        var id = prefs.getString("device_id", null)
        if (id.isNullOrBlank()) {
            id = "android_${UUID.randomUUID().toString().replace("-", "").take(8)}"
            prefs.edit().putString("device_id", id).apply()
        }
        return id
    }

    fun updateServerHost(newHost: String) {
        val cleanHost = newHost.trim().removePrefix("http://").removePrefix("https://").removeSuffix("/")
        if (cleanHost.isNotBlank()) {
            prefs.edit().putString("server_host", cleanHost).apply()
            _serverHost.value = cleanHost
            refreshDevices()
        }
    }

    fun updatePlaybackState(track: Track?, isPlaying: Boolean, positionMs: Long) {
        cachedTrack = track
        cachedIsPlaying = isPlaying
        cachedPositionSeconds = (positionMs / 1000.0).coerceAtLeast(0.0)
    }

    private fun startHeartbeatLoop() {
        heartbeatJob?.cancel()
        heartbeatJob = coroutineScope.launch {
            while (isActive) {
                try {
                    val req = RegisterDeviceRequest(
                        id = deviceId,
                        name = deviceName,
                        type = "mobile",
                        currentTrack = cachedTrack?.toConnectDto(),
                        isPlaying = cachedIsPlaying,
                        position = cachedPositionSeconds
                    )
                    val ok = client.registerDevice(_serverHost.value, port, req)
                    _isConnected.value = ok
                } catch (e: Exception) {
                    _isConnected.value = false
                }
                delay(4000)
            }
        }
    }

    private fun startPollLoop() {
        pollJob?.cancel()
        pollJob = coroutineScope.launch {
            while (isActive) {
                try {
                    val cmd = client.pollCommands(_serverHost.value, port, deviceId)
                    if (cmd != null) {
                        onCommandReceived?.invoke(cmd)
                    }
                } catch (e: Exception) {
                    // Fail-safe silent continue
                }
                delay(2500)
            }
        }
    }

    fun refreshDevices() {
        coroutineScope.launch {
            _isRefreshing.value = true
            try {
                val resp = client.getDevices(_serverHost.value, port)
                if (resp != null && resp.success) {
                    _isConnected.value = true
                    // Filter out this device so only other devices (PC, Laptop, Web) are shown
                    val otherDevices = (resp.devices ?: emptyList()).filter { it.id != deviceId }
                    _devices.value = otherDevices
                } else {
                    _devices.value = emptyList()
                }
            } catch (e: Exception) {
                _devices.value = emptyList()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    suspend fun transferPlaybackTo(
        targetDeviceId: String,
        track: Track,
        positionSeconds: Double
    ): Boolean {
        val req = SendCommandRequest(
            targetId = targetDeviceId,
            action = "transfer",
            track = track.toConnectDto(),
            position = positionSeconds,
            from = deviceName
        )
        return client.sendCommand(_serverHost.value, port, req)
    }

    suspend fun sendRemotePlay(targetDeviceId: String): Boolean {
        val req = SendCommandRequest(
            targetId = targetDeviceId,
            action = "play",
            from = deviceName
        )
        return client.sendCommand(_serverHost.value, port, req)
    }

    suspend fun sendRemotePause(targetDeviceId: String): Boolean {
        val req = SendCommandRequest(
            targetId = targetDeviceId,
            action = "pause",
            from = deviceName
        )
        return client.sendCommand(_serverHost.value, port, req)
    }

    suspend fun sendRemoteNext(targetDeviceId: String): Boolean {
        val req = SendCommandRequest(
            targetId = targetDeviceId,
            action = "next",
            from = deviceName
        )
        return client.sendCommand(_serverHost.value, port, req)
    }

    suspend fun sendRemotePrev(targetDeviceId: String): Boolean {
        val req = SendCommandRequest(
            targetId = targetDeviceId,
            action = "prev",
            from = deviceName
        )
        return client.sendCommand(_serverHost.value, port, req)
    }
}
