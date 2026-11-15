package com.sonic.music

import android.webkit.JavascriptInterface

class SonicNativeBridge(private val activity: MainActivity) {

    @JavascriptInterface
    fun onTrackChanged(title: String, artist: String, artworkUrl: String, durationSec: Long) {
        activity.runOnUiThread {
            activity.updateMediaNotification(title, artist, artworkUrl, durationSec)
        }
    }

    @JavascriptInterface
    fun onPlaybackStateChanged(isPlaying: Boolean) {
        activity.runOnUiThread {
            activity.updatePlaybackState(isPlaying)
        }
    }

    @JavascriptInterface
    fun onPlaybackPosition(positionSec: Long) {
        // Can be used if notification seeks
    }
}
