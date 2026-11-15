package com.sonic.music.data.local

import com.sonic.music.model.Track

data class DownloadedTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String = "SONiC Music",
    val artworkUrl: String,
    val localAudioPath: String,
    val localArtworkPath: String = "",
    val durationMs: Long = 0L,
    val fileSizeBytes: Long = 0L,
    val downloadedAt: Long = System.currentTimeMillis()
) {
    fun toTrack(): Track {
        return Track(
            id = id,
            title = title,
            artist = artist,
            album = album,
            artworkUrl = if (localArtworkPath.isNotEmpty()) "file://$localArtworkPath" else artworkUrl,
            audioUrl = "file://$localAudioPath",
            durationMs = durationMs,
            mood = "offline"
        )
    }
}
