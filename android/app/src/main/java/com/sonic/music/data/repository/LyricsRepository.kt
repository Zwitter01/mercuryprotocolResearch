package com.sonic.music.data.repository

import com.sonic.music.model.LyricLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LyricsRepository {

    // Curated synchronized time-stamped lyrics matching web player
    private val lyricsDatabase = mapOf(
        "O Maahi" to listOf(
            LyricLine(0.0, "O Maahi O Maahi"),
            LyricLine(5.2, "Dil toota toota hai"),
            LyricLine(11.0, "Tere bin jeena kya"),
            LyricLine(16.5, "Har saans adhuri hai"),
            LyricLine(22.0, "O Maahi O Maahi"),
            LyricLine(28.0, "Rabba tu jaanda ae"),
            LyricLine(34.0, "Mera dard pehchanda ae"),
            LyricLine(40.0, "Tere bin mar jaawan")
        ),
        "Satranga" to listOf(
            LyricLine(0.0, "Yeh ishq nahi aasan"),
            LyricLine(6.0, "Aag ka dariya hai"),
            LyricLine(12.0, "Aur doob ke jaana hai"),
            LyricLine(18.5, "Mera satranga yaar"),
            LyricLine(24.0, "Har rang tera pyaara"),
            LyricLine(30.0, "Tujhme hi kho jaana"),
            LyricLine(36.0, "Bas tere hi ho jaana")
        ),
        "Softly" to listOf(
            LyricLine(0.0, "Softly softly chal"),
            LyricLine(4.8, "Akhan vich akhan paake"),
            LyricLine(9.5, "Dil le gayi lutiya"),
            LyricLine(14.0, "Nakhre dikhaake"),
            LyricLine(19.2, "Softly softly walk"),
            LyricLine(24.0, "Baby gal sun le"),
            LyricLine(29.0, "Tere piche aawan main"),
            LyricLine(34.0, "Karan Aujla style")
        ),
        "Tauba Tauba" to listOf(
            LyricLine(0.0, "Husn tera tauba tauba"),
            LyricLine(5.0, "Style tera tauba tauba"),
            LyricLine(10.5, "Chori chori takdi ae"),
            LyricLine(15.2, "Agg wangu machdi ae"),
            LyricLine(20.0, "Tauba tauba kardi jawani"),
            LyricLine(25.5, "Vibe teri full mastani"),
            LyricLine(31.0, "Poora floor hilata tune")
        ),
        "Apna Bana Le" to listOf(
            LyricLine(0.0, "Tu mera koi na hoke bhi kuch laage"),
            LyricLine(6.5, "Kiya re jo bhi tune kaale jaadu jaise"),
            LyricLine(13.0, "Bhediya si aahat teri"),
            LyricLine(19.0, "Apna bana le piya"),
            LyricLine(25.0, "Apna bana le piya"),
            LyricLine(31.5, "Dil ke nagar me ghar bana le piya")
        )
    )

    suspend fun getLyricsForTrack(title: String, artist: String): List<LyricLine> = withContext(Dispatchers.IO) {
        val cleanTitle = title.split("(")[0].split("-")[0].trim()
        val match = lyricsDatabase.entries.firstOrNull { 
            cleanTitle.contains(it.key, ignoreCase = true) || it.key.contains(cleanTitle, ignoreCase = true) 
        }

        if (match != null) {
            match.value
        } else {
            // Graceful fallback for any song: generated aesthetic lyric lines
            listOf(
                LyricLine(0.0, "Listening to $cleanTitle"),
                LyricLine(5.0, "Performed by $artist"),
                LyricLine(12.0, "Full synchronized lyrics are available for popular hits"),
                LyricLine(20.0, "Enjoy 320kbps high-definition sound on SONiC"),
                LyricLine(30.0, "Music is the rhythm of the soul")
            )
        }
    }
}
