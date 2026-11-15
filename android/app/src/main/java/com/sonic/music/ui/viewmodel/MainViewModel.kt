package com.sonic.music.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonic.music.data.connect.ConnectCommandDto
import com.sonic.music.data.connect.ConnectDevice
import com.sonic.music.data.connect.SonicConnectManager
import com.sonic.music.data.download.SonicDownloadManager
import com.sonic.music.data.local.DownloadedTrack
import com.sonic.music.data.repository.LyricsRepository
import com.sonic.music.data.repository.MusicRepository
import com.sonic.music.model.LyricLine
import com.sonic.music.model.Track
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    SEARCH,
    LIBRARY
}

@OptIn(FlowPreview::class)
class MainViewModel(
    private val repository: MusicRepository = MusicRepository(),
    private val lyricsRepository: LyricsRepository = LyricsRepository()
) : ViewModel() {

    private var downloadManager: SonicDownloadManager? = null
    private var connectManager: SonicConnectManager? = null

    // Current navigation tab
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Playback state
    private val _currentTrack = MutableStateFlow<Track?>(repository.initialTrendingTracks.firstOrNull())
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPositionMs = MutableStateFlow(0L)
    val currentPositionMs: StateFlow<Long> = _currentPositionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(30000L) // Default 30s preview duration or full duration
    val durationMs: StateFlow<Long> = _durationMs.asStateFlow()

    private val _isShuffle = MutableStateFlow(false)
    val isShuffle: StateFlow<Boolean> = _isShuffle.asStateFlow()

    // 0 = Off, 1 = Repeat All, 2 = Repeat One
    private val _repeatMode = MutableStateFlow(0)
    val repeatMode: StateFlow<Int> = _repeatMode.asStateFlow()

    // Expanding Player Modals
    private val _isPlayerExpanded = MutableStateFlow(false)
    val isPlayerExpanded: StateFlow<Boolean> = _isPlayerExpanded.asStateFlow()

    private val _isQueueOpen = MutableStateFlow(false)
    val isQueueOpen: StateFlow<Boolean> = _isQueueOpen.asStateFlow()

    private val _isLyricsOpen = MutableStateFlow(false)
    val isLyricsOpen: StateFlow<Boolean> = _isLyricsOpen.asStateFlow()

    // Offline Downloads Screen
    private val _isDownloadsScreenOpen = MutableStateFlow(false)
    val isDownloadsScreenOpen: StateFlow<Boolean> = _isDownloadsScreenOpen.asStateFlow()

    private val _downloadedTracks = MutableStateFlow<List<DownloadedTrack>>(emptyList())
    val downloadedTracks: StateFlow<List<DownloadedTrack>> = _downloadedTracks.asStateFlow()

    private val _downloadProgress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val downloadProgress: StateFlow<Map<String, Int>> = _downloadProgress.asStateFlow()

    // SONiC Connect Multi-Device State
    private val _isConnectDialogOpen = MutableStateFlow(false)
    val isConnectDialogOpen: StateFlow<Boolean> = _isConnectDialogOpen.asStateFlow()

    private val _isConnectActive = MutableStateFlow(false)
    val isConnectActive: StateFlow<Boolean> = _isConnectActive.asStateFlow()

    private val _connectDevices = MutableStateFlow<List<ConnectDevice>>(emptyList())
    val connectDevices: StateFlow<List<ConnectDevice>> = _connectDevices.asStateFlow()

    private val _connectServerHost = MutableStateFlow("192.168.29.163")
    val connectServerHost: StateFlow<String> = _connectServerHost.asStateFlow()

    private val _isConnectRefreshing = MutableStateFlow(false)
    val isConnectRefreshing: StateFlow<Boolean> = _isConnectRefreshing.asStateFlow()

    // Synchronized Lyrics
    private val _lyrics = MutableStateFlow<List<LyricLine>>(emptyList())
    val lyrics: StateFlow<List<LyricLine>> = _lyrics.asStateFlow()

    // Trending & Recommended Feed
    private val _trendingTracks = MutableStateFlow(repository.initialTrendingTracks)
    val trendingTracks: StateFlow<List<Track>> = _trendingTracks.asStateFlow()

    // Playback Queue
    private val _playbackQueue = MutableStateFlow<List<Track>>(repository.initialTrendingTracks)
    val playbackQueue: StateFlow<List<Track>> = _playbackQueue.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Track>>(emptyList())
    val searchResults: StateFlow<List<Track>> = _searchResults.asStateFlow()

    init {
        // Automatic debounced live search as user types
        viewModelScope.launch {
            _searchQuery
                .debounce(350)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.trim().length >= 2) {
                        performSearch(query.trim())
                    } else {
                        _searchResults.value = emptyList()
                        _isSearching.value = false
                    }
                }
        }

        // Load lyrics for initial track
        _currentTrack.value?.let { loadLyricsForTrack(it) }
    }

    fun initDownloadManager(context: Context) {
        if (downloadManager == null) {
            val mgr = SonicDownloadManager(context.applicationContext)
            downloadManager = mgr
            viewModelScope.launch {
                mgr.downloadProgress.collect { progressMap ->
                    _downloadProgress.value = progressMap
                }
            }
            loadDownloadedTracks()
        }
    }

    fun initConnectManager(context: Context, onCommandAction: (ConnectCommandDto) -> Unit) {
        if (connectManager == null) {
            val mgr = SonicConnectManager(context.applicationContext, viewModelScope)
            connectManager = mgr

            mgr.onCommandReceived = { cmd ->
                if (cmd.action == "transfer" && cmd.track != null) {
                    val track = cmd.track.toTrack()
                    playTrack(track)
                }
                onCommandAction(cmd)
            }

            viewModelScope.launch {
                mgr.devices.collect { devs -> _connectDevices.value = devs }
            }
            viewModelScope.launch {
                mgr.isConnected.collect { conn -> _isConnectActive.value = conn }
            }
            viewModelScope.launch {
                mgr.serverHost.collect { host -> _connectServerHost.value = host }
            }
            viewModelScope.launch {
                mgr.isRefreshing.collect { ref -> _isConnectRefreshing.value = ref }
            }
        }
    }

    fun loadDownloadedTracks() {
        viewModelScope.launch {
            downloadManager?.let { mgr ->
                _downloadedTracks.value = mgr.getAllDownloadedTracks()
            }
        }
    }

    fun setDownloadsScreenOpen(open: Boolean) {
        _isDownloadsScreenOpen.value = open
    }

    fun setConnectDialogOpen(open: Boolean) {
        _isConnectDialogOpen.value = open
        if (open) {
            connectManager?.refreshDevices()
        }
    }

    fun refreshConnectDevices() {
        connectManager?.refreshDevices()
    }

    fun updateConnectServerHost(newHost: String) {
        connectManager?.updateServerHost(newHost)
    }

    fun transferPlaybackToDevice(targetDeviceId: String, onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val cur = _currentTrack.value
            val pos = (_currentPositionMs.value / 1000.0).coerceAtLeast(0.0)
            if (cur != null && connectManager != null) {
                val ok = connectManager!!.transferPlaybackTo(targetDeviceId, cur, pos)
                if (ok) {
                    // Pause local audio since it has transferred to the PC/web player
                    _isPlaying.value = false
                }
                onComplete(ok)
            } else {
                onComplete(false)
            }
        }
    }

    fun sendRemotePlayPause(targetDeviceId: String, currentlyPlaying: Boolean) {
        viewModelScope.launch {
            if (currentlyPlaying) {
                connectManager?.sendRemotePause(targetDeviceId)
            } else {
                connectManager?.sendRemotePlay(targetDeviceId)
            }
            connectManager?.refreshDevices()
        }
    }

    fun sendRemoteNext(targetDeviceId: String) {
        viewModelScope.launch {
            connectManager?.sendRemoteNext(targetDeviceId)
            connectManager?.refreshDevices()
        }
    }

    fun sendRemotePrev(targetDeviceId: String) {
        viewModelScope.launch {
            connectManager?.sendRemotePrev(targetDeviceId)
            connectManager?.refreshDevices()
        }
    }

    fun downloadTrack(track: Track) {
        viewModelScope.launch {
            downloadManager?.let { mgr ->
                mgr.downloadTrack(track)
                loadDownloadedTracks()
            }
        }
    }

    fun removeDownload(trackId: String) {
        viewModelScope.launch {
            downloadManager?.let { mgr ->
                mgr.removeDownload(trackId)
                loadDownloadedTracks()
            }
        }
    }

    fun isTrackDownloaded(trackId: String): Boolean {
        return _downloadedTracks.value.any { it.id == trackId }
    }

    fun setScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.trim().length >= 2) {
            _isSearching.value = true
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        _isSearching.value = false
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            _isSearching.value = true
            val result = repository.searchTracks(query)
            result.onSuccess { tracks ->
                _searchResults.value = tracks
            }.onFailure {
                _searchResults.value = emptyList()
            }
            _isSearching.value = false
        }
    }

    fun playTrack(track: Track, newQueue: List<Track>? = null) {
        _currentTrack.value = track
        _isPlaying.value = true
        _currentPositionMs.value = 0L
        if (track.durationMs > 0) {
            _durationMs.value = track.durationMs
        } else {
            _durationMs.value = 30000L
        }
        if (newQueue != null && newQueue.isNotEmpty()) {
            _playbackQueue.value = newQueue
        }
        loadLyricsForTrack(track)
        connectManager?.updatePlaybackState(track, true, 0L)
    }

    private fun loadLyricsForTrack(track: Track) {
        viewModelScope.launch {
            val lines = lyricsRepository.getLyricsForTrack(track.title, track.artist)
            _lyrics.value = lines
        }
    }

    fun setPlaying(playing: Boolean) {
        _isPlaying.value = playing
        connectManager?.updatePlaybackState(_currentTrack.value, playing, _currentPositionMs.value)
    }

    fun updatePlaybackProgress(positionMs: Long, durationMs: Long) {
        _currentPositionMs.value = positionMs
        if (durationMs > 0) {
            _durationMs.value = durationMs
        }
        connectManager?.updatePlaybackState(_currentTrack.value, _isPlaying.value, positionMs)
    }

    fun setPlayerExpanded(expanded: Boolean) {
        _isPlayerExpanded.value = expanded
        if (!expanded) {
            _isQueueOpen.value = false
            _isLyricsOpen.value = false
        }
    }

    fun toggleQueue() {
        _isQueueOpen.value = !_isQueueOpen.value
    }

    fun toggleLyrics() {
        _isLyricsOpen.value = !_isLyricsOpen.value
    }

    fun toggleShuffle() {
        _isShuffle.value = !_isShuffle.value
    }

    fun toggleRepeat() {
        _repeatMode.value = (_repeatMode.value + 1) % 3
    }

    fun toggleLike(trackId: String) {
        _currentTrack.value?.let { cur ->
            if (cur.id == trackId) {
                _currentTrack.value = cur.copy(isLiked = !cur.isLiked)
            }
        }
        _trendingTracks.value = _trendingTracks.value.map {
            if (it.id == trackId) it.copy(isLiked = !it.isLiked) else it
        }
        _searchResults.value = _searchResults.value.map {
            if (it.id == trackId) it.copy(isLiked = !it.isLiked) else it
        }
        _playbackQueue.value = _playbackQueue.value.map {
            if (it.id == trackId) it.copy(isLiked = !it.isLiked) else it
        }
    }

    fun getNextTrack(): Track? {
        val queue = _playbackQueue.value
        if (queue.isEmpty()) return null

        if (_repeatMode.value == 2) {
            return _currentTrack.value
        }

        if (_isShuffle.value) {
            val remaining = queue.filter { it.id != _currentTrack.value?.id }
            return if (remaining.isNotEmpty()) remaining.random() else queue.first()
        }

        val currentIndex = queue.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex == -1) return queue.first()

        val nextIndex = currentIndex + 1
        return if (nextIndex < queue.size) {
            queue[nextIndex]
        } else if (_repeatMode.value == 1) {
            queue.first()
        } else {
            null
        }
    }

    fun getPreviousTrack(): Track? {
        val queue = _playbackQueue.value
        if (queue.isEmpty()) return null

        val currentIndex = queue.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex <= 0) return queue.last()

        return queue[currentIndex - 1]
    }
}
