package com.sonic.music.data.connect

import com.google.gson.annotations.SerializedName
import com.sonic.music.model.Track

data class ConnectTrackDto(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("album") val album: String? = null,
    @SerializedName("artwork") val artwork: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("fullStreamUrl") val fullStreamUrl: String?,
    @SerializedName("durationMs") val durationMs: Long?
) {
    fun toTrack(): Track {
        val streamUrl = when {
            !fullStreamUrl.isNullOrBlank() -> fullStreamUrl
            !previewUrl.isNullOrBlank() -> previewUrl
            else -> ""
        }
        return Track(
            id = id ?: "remote_${System.currentTimeMillis()}",
            title = title ?: "Unknown Track",
            artist = artist ?: "Unknown Artist",
            album = album ?: "SONiC Stream",
            artworkUrl = artwork ?: "",
            audioUrl = streamUrl,
            durationMs = durationMs ?: 30000L,
            mood = "Connect",
            isLiked = false
        )
    }
}

fun Track.toConnectDto(): ConnectTrackDto {
    return ConnectTrackDto(
        id = id,
        title = title,
        artist = artist,
        album = album,
        artwork = artworkUrl,
        previewUrl = audioUrl,
        fullStreamUrl = audioUrl,
        durationMs = durationMs
    )
}

data class ConnectDevice(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String, // "desktop" or "mobile"
    @SerializedName("currentTrack") val currentTrack: ConnectTrackDto?,
    @SerializedName("isPlaying") val isPlaying: Boolean = false,
    @SerializedName("lastSeen") val lastSeen: Long = 0L
)

data class ConnectCommandDto(
    @SerializedName("action") val action: String, // "transfer", "play", "pause", "next", "prev", "seek"
    @SerializedName("track") val track: ConnectTrackDto?,
    @SerializedName("position") val position: Double?,
    @SerializedName("from") val from: String?,
    @SerializedName("time") val time: Long?
)

data class RegisterDeviceRequest(
    val id: String,
    val name: String,
    val type: String,
    val currentTrack: ConnectTrackDto?,
    val isPlaying: Boolean,
    val position: Double
)

data class SendCommandRequest(
    val targetId: String,
    val action: String,
    val track: ConnectTrackDto? = null,
    val position: Double = 0.0,
    val from: String
)

data class DevicesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("localIp") val localIp: String?,
    @SerializedName("localUrl") val localUrl: String?,
    @SerializedName("devices") val devices: List<ConnectDevice>?
)

data class PollResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("command") val command: ConnectCommandDto?
)

data class GeneralResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("count") val count: Int? = null,
    @SerializedName("error") val error: String? = null
)
