package com.sonic.music.model

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String = "SONiC Music",
    val artworkUrl: String,
    val audioUrl: String,
    val durationMs: Long = 0L,
    val mood: String = "auto",
    val isLiked: Boolean = false
)
