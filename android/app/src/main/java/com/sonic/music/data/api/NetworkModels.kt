package com.sonic.music.data.api

import com.google.gson.annotations.SerializedName

data class ItunesSearchResponse(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<ItunesTrackDto>
)

data class ItunesTrackDto(
    @SerializedName("trackId") val trackId: Long?,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("collectionName") val collectionName: String?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?,
    @SerializedName("artworkUrl60") val artworkUrl60: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long?,
    @SerializedName("primaryGenreName") val primaryGenreName: String?,
    @SerializedName("releaseDate") val releaseDate: String?
)
