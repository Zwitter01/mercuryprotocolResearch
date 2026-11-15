package com.sonic.music.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SonicDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "sonic_music.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_DOWNLOADS = "downloaded_tracks"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_ARTIST = "artist"
        const val COL_ALBUM = "album"
        const val COL_ARTWORK_URL = "artwork_url"
        const val COL_LOCAL_AUDIO_PATH = "local_audio_path"
        const val COL_LOCAL_ARTWORK_PATH = "local_artwork_path"
        const val COL_DURATION_MS = "duration_ms"
        const val COL_FILE_SIZE_BYTES = "file_size_bytes"
        const val COL_DOWNLOADED_AT = "downloaded_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_DOWNLOADS (
                $COL_ID TEXT PRIMARY KEY,
                $COL_TITLE TEXT NOT NULL,
                $COL_ARTIST TEXT NOT NULL,
                $COL_ALBUM TEXT,
                $COL_ARTWORK_URL TEXT,
                $COL_LOCAL_AUDIO_PATH TEXT NOT NULL,
                $COL_LOCAL_ARTWORK_PATH TEXT,
                $COL_DURATION_MS INTEGER,
                $COL_FILE_SIZE_BYTES INTEGER,
                $COL_DOWNLOADED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOWNLOADS")
        onCreate(db)
    }

    fun getAllDownloadedTracks(): List<DownloadedTrack> {
        val list = mutableListOf<DownloadedTrack>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DOWNLOADS,
            null,
            null,
            null,
            null,
            null,
            "$COL_DOWNLOADED_AT DESC"
        )

        cursor.use { c ->
            val idxId = c.getColumnIndexOrThrow(COL_ID)
            val idxTitle = c.getColumnIndexOrThrow(COL_TITLE)
            val idxArtist = c.getColumnIndexOrThrow(COL_ARTIST)
            val idxAlbum = c.getColumnIndexOrThrow(COL_ALBUM)
            val idxArtUrl = c.getColumnIndexOrThrow(COL_ARTWORK_URL)
            val idxAudioPath = c.getColumnIndexOrThrow(COL_LOCAL_AUDIO_PATH)
            val idxArtPath = c.getColumnIndexOrThrow(COL_LOCAL_ARTWORK_PATH)
            val idxDuration = c.getColumnIndexOrThrow(COL_DURATION_MS)
            val idxFileSize = c.getColumnIndexOrThrow(COL_FILE_SIZE_BYTES)
            val idxDate = c.getColumnIndexOrThrow(COL_DOWNLOADED_AT)

            while (c.moveToNext()) {
                list.add(
                    DownloadedTrack(
                        id = c.getString(idxId),
                        title = c.getString(idxTitle),
                        artist = c.getString(idxArtist),
                        album = c.getString(idxAlbum) ?: "SONiC Music",
                        artworkUrl = c.getString(idxArtUrl) ?: "",
                        localAudioPath = c.getString(idxAudioPath),
                        localArtworkPath = c.getString(idxArtPath) ?: "",
                        durationMs = c.getLong(idxDuration),
                        fileSizeBytes = c.getLong(idxFileSize),
                        downloadedAt = c.getLong(idxDate)
                    )
                )
            }
        }
        return list
    }

    fun insertOrReplace(track: DownloadedTrack) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ID, track.id)
            put(COL_TITLE, track.title)
            put(COL_ARTIST, track.artist)
            put(COL_ALBUM, track.album)
            put(COL_ARTWORK_URL, track.artworkUrl)
            put(COL_LOCAL_AUDIO_PATH, track.localAudioPath)
            put(COL_LOCAL_ARTWORK_PATH, track.localArtworkPath)
            put(COL_DURATION_MS, track.durationMs)
            put(COL_FILE_SIZE_BYTES, track.fileSizeBytes)
            put(COL_DOWNLOADED_AT, track.downloadedAt)
        }
        db.insertWithOnConflict(TABLE_DOWNLOADS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun deleteTrack(id: String) {
        val db = writableDatabase
        db.delete(TABLE_DOWNLOADS, "$COL_ID = ?", arrayOf(id))
    }

    fun isTrackDownloaded(id: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DOWNLOADS,
            arrayOf(COL_ID),
            "$COL_ID = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        return cursor.use { it.count > 0 }
    }
}
