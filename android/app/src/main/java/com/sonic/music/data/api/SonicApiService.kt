package com.sonic.music.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface SonicApiService {

    @GET("search")
    suspend fun searchSongs(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("entity") entity: String = "song",
        @Query("limit") limit: Int = 30
    ): ItunesSearchResponse
}
