package com.sonic.music.data.repository

import com.sonic.music.data.api.NetworkClient
import com.sonic.music.data.api.SonicApiService
import com.sonic.music.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicRepository(
    private val apiService: SonicApiService = NetworkClient.apiService
) {

    // Default curated trending playlist for initial load
    val initialTrendingTracks = listOf(
        Track(
            id = "t1",
            title = "O Maahi",
            artist = "Arijit Singh",
            album = "Dunki",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/37/ba/7c/37ba7c53-b0b3-f006-25f0-6a1ea0aa1366/8903431959739_cover.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview116/v4/05/9a/50/059a5099-ce12-140a-5c17-1f4a9b6c0e5a/mzaf_10526848261884488975.plus.aac.p.m4a",
            mood = "romantic"
        ),
        Track(
            id = "t2",
            title = "Satranga",
            artist = "Arijit Singh, Shreyas Puranik",
            album = "ANIMAL",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/c3/8c/d7/c38cd731-bf87-bf5e-14aa-d6ecfae26c63/8902894361592.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview116/v4/6c/fb/84/6cfb84e3-3b17-7489-08a8-0d12e6bf5420/mzaf_13596790938440788648.plus.aac.p.m4a",
            mood = "romantic"
        ),
        Track(
            id = "t3",
            title = "Softly",
            artist = "Karan Aujla, Ikky",
            album = "Four Me",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/3c/69/bb/3c69bb1c-8b89-2c70-7629-6881c1c9cb27/197189178942.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview126/v4/55/e8/30/55e830f6-9f70-5b12-9c16-b8a928e2ad71/mzaf_5813725515250482098.plus.aac.p.m4a",
            mood = "punjabi"
        ),
        Track(
            id = "t4",
            title = "Tauba Tauba",
            artist = "Karan Aujla",
            album = "Bad Newz",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/05/dc/17/05dc1719-74d7-ea88-4672-031023773e44/8903431980641_cover.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/c9/28/7f/c9287f3b-fa00-bc56-3c22-b2586b6279f0/mzaf_6433292440316417742.plus.aac.p.m4a",
            mood = "party"
        ),
        Track(
            id = "t5",
            title = "Apna Bana Le",
            artist = "Arijit Singh, Sachin-Jigar",
            album = "Bhediya",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/71/e6/4b/71e64b63-a3fe-89be-c965-c9fa49e9d6d3/8903431908478_cover.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/7c/27/b5/7c27b5f5-748d-691c-1481-420ef3577d24/mzaf_10204737754320984920.plus.aac.p.m4a",
            mood = "romantic"
        ),
        Track(
            id = "t6",
            title = "Winning Speech",
            artist = "Karan Aujla",
            album = "Single",
            artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/37/ef/55/37ef554a-5f33-1ef9-813a-a1977e2311f9/197189912065.jpg/600x600bb.jpg",
            audioUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview116/v4/d5/92/95/d5929555-4674-c8c3-42eb-f6d39adbc6fb/mzaf_14959146194787968597.plus.aac.p.m4a",
            mood = "punjabi"
        )
    )

    suspend fun searchTracks(query: String): Result<List<Track>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchSongs(term = query)
            val tracks = response.results
                .filter { it.trackName != null && it.previewUrl != null }
                .map { dto ->
                    val highResArt = (dto.artworkUrl100 ?: dto.artworkUrl60 ?: "")
                        .replace("100x100bb.jpg", "600x600bb.jpg")
                        .replace("60x60bb.jpg", "600x600bb.jpg")

                    Track(
                        id = (dto.trackId ?: System.currentTimeMillis()).toString(),
                        title = dto.trackName ?: "Unknown Track",
                        artist = dto.artistName ?: "Unknown Artist",
                        album = dto.collectionName ?: "Single",
                        artworkUrl = highResArt,
                        audioUrl = dto.previewUrl ?: "",
                        durationMs = dto.trackTimeMillis ?: 0L,
                        mood = "auto"
                    )
                }
            Result.success(tracks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
