package com.sonic.music

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.net.URL

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private var wakeLock: PowerManager.WakeLock? = null
    private var isPlaying: Boolean = false
    private var currentTitle: String = "SONiC Music"
    private var currentArtist: String = "100% Free Full Music"
    private var currentArtworkBitmap: Bitmap? = null

    companion object {
        const val CHANNEL_ID = "sonic_music_playback"
        const val NOTIFICATION_ID = 1001
        const val ACTION_PLAY_PAUSE = "com.sonic.music.ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "com.sonic.music.ACTION_NEXT"
        const val ACTION_PREV = "com.sonic.music.ACTION_PREV"
        const val ACTION_TAB = "com.sonic.music.ACTION_TAB"
        const val ACTION_EVAL = "com.sonic.music.ACTION_EVAL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aurora Noir status bar and navigation bar blending with edge-to-edge touch support
        window.statusBarColor = android.graphics.Color.parseColor("#050409")
        window.navigationBarColor = android.graphics.Color.parseColor("#050409")
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Sonic:AudioWakeLock")

        createNotificationChannel()
        setupWebView()
        handleIntentAction(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntentAction(intent)
    }

    private fun handleIntentAction(intent: Intent?) {
        if (!::webView.isInitialized) return
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                runOnUiThread {
                    webView.evaluateJavascript("window.sonic?.togglePlayPause?.();", null)
                }
            }
            ACTION_NEXT -> {
                runOnUiThread {
                    webView.evaluateJavascript("window.sonic?.playNext?.(false);", null)
                }
            }
            ACTION_PREV -> {
                runOnUiThread {
                    webView.evaluateJavascript("window.sonic?.playPrevious?.();", null)
                }
            }
            ACTION_TAB -> {
                val tab = intent.getStringExtra("tab") ?: "home"
                switchTabDirectly(tab)
            }
            ACTION_EVAL -> {
                val js = intent.getStringExtra("js") ?: ""
                runOnUiThread {
                    webView.evaluateJavascript(js, null)
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView = WebView(this).apply {
            setBackgroundColor(android.graphics.Color.parseColor("#050409"))
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                mediaPlaybackRequiresUserGesture = false
                allowFileAccess = true
                allowContentAccess = true
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
                useWideViewPort = false
                loadWithOverviewMode = false
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            addJavascriptInterface(SonicNativeBridge(this@MainActivity), "SonicNative")

            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    android.util.Log.d("SonicWeb", "${consoleMessage?.message()}")
                    return true
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    val url = request?.url ?: return null
                    val path = url.path ?: ""

                    if (path.contains("/api/search")) {
                        val q = url.getQueryParameter("q") ?: ""
                        val json = SonicApiResolver.searchMultiCatalog(q)
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/fullsong")) {
                        val q = url.getQueryParameter("q") ?: ""
                        val json = SonicApiResolver.resolveFullSong(q)
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/lyrics")) {
                        val track = url.getQueryParameter("track") ?: ""
                        val artist = url.getQueryParameter("artist") ?: ""
                        val json = SonicApiResolver.resolveLyrics(track, artist)
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/newreleases")) {
                        val json = SonicApiResolver.getLiveNewReleases()
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/connect/devices")) {
                        val json = """{"success":true,"devices":[]}"""
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/connect/register")) {
                        val json = """{"success":true}"""
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    if (path.contains("/api/connect/poll")) {
                        val json = """{"success":true,"command":null}"""
                        val stream = ByteArrayInputStream(json.toByteArray(Charsets.UTF_8))
                        return WebResourceResponse("application/json", "UTF-8", stream)
                    }

                    return super.shouldInterceptRequest(view, request)
                }
            }
        }

        setContentView(webView)
        webView.loadUrl("file:///android_asset/index.html")
    }

    fun updateMediaNotification(title: String, artist: String, artworkUrl: String, durationSec: Long) {
        currentTitle = title
        currentArtist = artist

        if (artworkUrl.isNotBlank() && artworkUrl.startsWith("http")) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val stream = URL(artworkUrl).openStream()
                    val bitmap = BitmapFactory.decodeStream(stream)
                    withContext(Dispatchers.Main) {
                        currentArtworkBitmap = bitmap
                        showNotification()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        currentArtworkBitmap = null
                        showNotification()
                    }
                }
            }
        } else {
            currentArtworkBitmap = null
            showNotification()
        }
    }

    fun switchTabDirectly(tab: String) {
        runOnUiThread {
            webView.evaluateJavascript("window.switchMobileTab?.('$tab');", null)
        }
    }

    fun updatePlaybackState(playing: Boolean) {
        isPlaying = playing
        if (playing) {
            if (wakeLock?.isHeld == false) {
                wakeLock?.acquire(120 * 60 * 1000L) // 2 hours safe playback
            }
        } else {
            if (wakeLock?.isHeld == true) {
                wakeLock?.release()
            }
        }
        showNotification()
    }

    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val openPendingIntent = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIntent = Intent(this, MainActivity::class.java).apply {
            action = ACTION_PLAY_PAUSE
        }
        val playPausePendingIntent = PendingIntent.getActivity(
            this, 1, playPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent = Intent(this, MainActivity::class.java).apply {
            action = ACTION_NEXT
        }
        val nextPendingIntent = PendingIntent.getActivity(
            this, 2, nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val prevIntent = Intent(this, MainActivity::class.java).apply {
            action = ACTION_PREV
        }
        val prevPendingIntent = PendingIntent.getActivity(
            this, 3, prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIcon = if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(currentTitle)
            .setContentText(currentArtist)
            .setSubText("SONiC Music")
            .setContentIntent(openPendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(isPlaying)
            .addAction(android.R.drawable.ic_media_previous, "Previous", prevPendingIntent)
            .addAction(playPauseIcon, if (isPlaying) "Pause" else "Play", playPausePendingIntent)
            .addAction(android.R.drawable.ic_media_next, "Next", nextPendingIntent)

        currentArtworkBitmap?.let {
            builder.setLargeIcon(it)
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SONiC Music Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background audio controls for SONiC Music"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Keep playback running in background on back press
            moveTaskToBack(true)
        }
    }

    override fun onDestroy() {
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
        }
        super.onDestroy()
    }
}
