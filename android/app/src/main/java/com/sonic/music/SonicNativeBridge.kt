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

    @JavascriptInterface
    fun getLiveNewReleases(): String {
        return try {
            SonicApiResolver.getLiveNewReleases()
        } catch (e: Exception) {
            """{"status":"error","message":"${e.message}","data":[]}"""
        }
    }

    @JavascriptInterface
    fun searchMultiCatalog(query: String): String {
        return try {
            SonicApiResolver.searchMultiCatalog(query)
        } catch (e: Exception) {
            """{"status":"error","message":"${e.message}","data":[]}"""
        }
    }

    @JavascriptInterface
    fun resolveFullSong(query: String): String {
        return try {
            SonicApiResolver.resolveFullSong(query)
        } catch (e: Exception) {
            """{"status":"error","message":"${e.message}"}"""
        }
    }

    @JavascriptInterface
    fun resolveLyrics(track: String, artist: String): String {
        return try {
            SonicApiResolver.resolveLyrics(track, artist)
        } catch (e: Exception) {
            """{"status":"error","message":"${e.message}"}"""
        }
    }
}
