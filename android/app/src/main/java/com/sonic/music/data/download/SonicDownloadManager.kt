package com.sonic.music.data.download

import android.content.Context
import com.sonic.music.data.local.DownloadedTrack
import com.sonic.music.data.local.SonicDatabaseHelper
import com.sonic.music.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class SonicDownloadManager(private val context: Context) {

    private val dbHelper = SonicDatabaseHelper(context)

    // TrackId -> Download Percentage (0..100)
    private val _downloadProgress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val downloadProgress: StateFlow<Map<String, Int>> = _downloadProgress.asStateFlow()

    private val offlineDir: File by lazy {
        val dir = File(context.filesDir, "sonic_offline")
        if (!dir.exists()) dir.mkdirs()
        dir
    }

    suspend fun downloadTrack(track: Track): Result<DownloadedTrack> = withContext(Dispatchers.IO) {
        try {
            _downloadProgress.value = _downloadProgress.value + (track.id to 0)

            val safeId = track.id.replace(Regex("[^a-zA-Z0-9_]"), "_")
            val audioFile = File(offlineDir, "audio_${safeId}.m4a")
            val artFile = File(offlineDir, "art_${safeId}.jpg")

            // 1. Download Audio File
            downloadFileWithProgress(track.audioUrl, audioFile) { percent ->
                _downloadProgress.value = _downloadProgress.value + (track.id to (percent * 0.85).toInt())
            }

            // 2. Download Artwork File (if available)
            if (track.artworkUrl.isNotEmpty()) {
                try {
                    downloadFileWithProgress(track.artworkUrl, artFile) {
                        _downloadProgress.value = _downloadProgress.value + (track.id to 95)
                    }
                } catch (e: Exception) {
                    // Non-critical: artwork download failure won't prevent song playback
                }
            }

            // 3. Save to Database
            val downloadedTrack = DownloadedTrack(
                id = track.id,
                title = track.title,
                artist = track.artist,
                album = track.album,
                artworkUrl = track.artworkUrl,
                localAudioPath = audioFile.absolutePath,
                localArtworkPath = if (artFile.exists()) artFile.absolutePath else "",
                durationMs = track.durationMs,
                fileSizeBytes = audioFile.length(),
                downloadedAt = System.currentTimeMillis()
            )

            dbHelper.insertOrReplace(downloadedTrack)

            _downloadProgress.value = _downloadProgress.value - track.id
            Result.success(downloadedTrack)
        } catch (e: Exception) {
            _downloadProgress.value = _downloadProgress.value - track.id
            Result.failure(e)
        }
    }

    private fun downloadFileWithProgress(
        fileUrl: String,
        destinationFile: File,
        onProgress: (Int) -> Unit
    ) {
        val url = URL(fileUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.connect()

        if (connection.responseCode !in 200..299) {
            throw Exception("Server returned HTTP ${connection.responseCode}")
        }

        val fileLength = connection.contentLength
        val input = connection.inputStream
        val output = FileOutputStream(destinationFile)

        val buffer = ByteArray(4096)
        var total: Long = 0
        var count: Int

        try {
            while (input.read(buffer).also { count = it } != -1) {
                total += count
                output.write(buffer, 0, count)
                if (fileLength > 0) {
                    val percent = ((total * 100) / fileLength).toInt().coerceIn(0, 100)
                    onProgress(percent)
                }
            }
            output.flush()
        } finally {
            output.close()
            input.close()
            connection.disconnect()
        }
    }

    suspend fun removeDownload(trackId: String) = withContext(Dispatchers.IO) {
        val safeId = trackId.replace(Regex("[^a-zA-Z0-9_]"), "_")
        val audioFile = File(offlineDir, "audio_${safeId}.m4a")
        val artFile = File(offlineDir, "art_${safeId}.jpg")

        if (audioFile.exists()) audioFile.delete()
        if (artFile.exists()) artFile.delete()

        dbHelper.deleteTrack(trackId)
    }

    suspend fun getAllDownloadedTracks(): List<DownloadedTrack> = withContext(Dispatchers.IO) {
        dbHelper.getAllDownloadedTracks()
    }

    fun isTrackDownloaded(trackId: String): Boolean {
        return dbHelper.isTrackDownloaded(trackId)
    }
}
