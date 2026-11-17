package com.sonic.music

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object SonicApiResolver {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    private val fullSongCache = mutableMapOf<String, String>()
    private val lyricsCache = mutableMapOf<String, String>()
    private val searchCache = mutableMapOf<String, String>()
    private const val SC_CLIENT_ID = "qLcgHhXgeEFFaSolTB9I71LBsqncsD93"

    private fun cleanText(str: String): String {
        return str.replace("&quot;", "\"")
            .replace("&#039;", "'")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
    }

    private fun hasCJK(str: String): Boolean {
        return str.any { it.code in 0x3040..0x30FF || it.code in 0x3400..0x4DBF || it.code in 0x4E00..0x9FFF }
    }

    private fun extractVariants(raw: String): List<String> {
        val list = mutableListOf(raw.trim())
        if (raw.contains("-") || raw.contains("\u2013") || raw.contains("\u2014") || raw.contains("/") || raw.contains(":")) {
            val parts = raw.split(Regex("""[\-\u2013\u2014/:]""")).map { it.trim() }.filter { it.length > 1 }
            parts.forEach { if (!list.contains(it)) list.add(it) }
        }
        val cleaned = raw.replace(Regex("""[\(\)\[\]\-]"""), " ").replace(Regex("""\s+"""), " ").trim()
        if (cleaned.isNotBlank() && !list.contains(cleaned)) list.add(cleaned)
        return list
    }

    fun searchMultiCatalog(query: String): String {
        val cleanQ = query.trim()
        val cacheKey = cleanQ.lowercase()
        searchCache[cacheKey]?.let { return it }

        val variants = extractVariants(cleanQ)
        val isCjk = hasCJK(cleanQ)

        val tracksArr = JSONArray()
        val artistsArr = JSONArray()
        val albumsArr = JSONArray()
        val seenTracks = mutableSetOf<String>()
        val seenArtists = mutableSetOf<String>()
        val seenAlbums = mutableSetOf<String>()

        fun addTrack(t: JSONObject) {
            val title = t.optString("title", "").lowercase()
            val artist = t.optString("artist", "").lowercase()
            val key = "${title}_${artist}".replace(Regex("[^a-z0-9]"), "")
            if (!seenTracks.contains(key)) {
                seenTracks.add(key)
                tracksArr.put(t)
            }
        }

        // 1. Search JioSaavn first (Fastest 300ms, 320k catalog)
        for (v in variants) {
            if (hasCJK(v) && !v.any { c -> c in 'a'..'z' || c in 'A'..'Z' || c in '0'..'9' }) continue
            try {
                val encoded = URLEncoder.encode(v, "UTF-8")
                val url = "https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=15&p=1&q=$encoded"
                val req = Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0")
                    .build()

                httpClient.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        val bodyStr = resp.body?.string() ?: ""
                        val root = JSONObject(bodyStr)
                        val results = root.optJSONArray("results")
                        if (results != null) {
                            for (i in 0 until results.length()) {
                                val s = results.getJSONObject(i)
                                val durSec = s.optString("duration", "215").toIntOrNull() ?: 215
                                val tObj = JSONObject().apply {
                                    put("id", "saavn_${s.optString("id", "")}")
                                    put("title", cleanText(s.optString("song", cleanQ)))
                                    put("artist", cleanText(s.optString("primary_artists", s.optString("singers", "Unknown"))))
                                    put("album", cleanText(s.optString("album", "Single")))
                                    put("artwork", s.optString("image", "").replace("150x150", "500x500"))
                                    put("previewUrl", "")
                                    put("durationMs", durSec * 1000L)
                                    put("releaseDate", s.optString("year", ""))
                                }
                                addTrack(tObj)
                            }
                        }
                    }
                }
            } catch (e: Exception) {}
            if (tracksArr.length() >= 10) break
        }

        // 2. Search iTunes across storefronts (JP for CJK, IN for general)
        val country = if (isCjk) "jp" else "in"
        for (v in variants.take(2)) {
            try {
                val encoded = URLEncoder.encode(v, "UTF-8")
                val url = "https://itunes.apple.com/search?term=$encoded&country=$country&media=music&entity=song,musicArtist,album&limit=20"
                val req = Request.Builder().url(url).build()

                httpClient.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        val bodyStr = resp.body?.string() ?: ""
                        val root = JSONObject(bodyStr)
                        val results = root.optJSONArray("results")
                        if (results != null) {
                            for (i in 0 until results.length()) {
                                val item = results.getJSONObject(i)
                                val kind = item.optString("kind", "")
                                val trackName = item.optString("trackName", "")

                                if (kind == "song" || trackName.isNotBlank()) {
                                    val tObj = JSONObject().apply {
                                        put("id", item.optString("trackId", (100000..999999).random().toString()))
                                        put("title", trackName)
                                        put("artist", item.optString("artistName", "Unknown Artist"))
                                        put("album", item.optString("collectionName", "Single"))
                                        put("artwork", item.optString("artworkUrl100", "").replace("100x100bb", "600x600bb"))
                                        put("previewUrl", item.optString("previewUrl", ""))
                                        put("durationMs", item.optLong("trackTimeMillis", 215000L))
                                        put("releaseDate", item.optString("releaseDate", ""))
                                    }
                                    addTrack(tObj)
                                }

                                val wrapperType = item.optString("wrapperType", "")
                                val artistName = item.optString("artistName", "")
                                if ((wrapperType == "artist" || artistName.isNotBlank()) && !seenArtists.contains(artistName.lowercase())) {
                                    seenArtists.add(artistName.lowercase())
                                    val aObj = JSONObject().apply {
                                        put("id", item.optString("artistId", (100000..999999).random().toString()))
                                        put("name", artistName)
                                        put("genre", item.optString("primaryGenreName", "Artist"))
                                        put("image", item.optString("artworkUrl100", "").replace("100x100bb", "600x600bb"))
                                    }
                                    artistsArr.put(aObj)
                                }

                                val collectionType = item.optString("collectionType", "")
                                val collectionName = item.optString("collectionName", "")
                                if ((collectionType == "Album" || collectionName.isNotBlank()) && !seenAlbums.contains(collectionName.lowercase())) {
                                    seenAlbums.add(collectionName.lowercase())
                                    val albObj = JSONObject().apply {
                                        put("id", item.optString("collectionId", (100000..999999).random().toString()))
                                        put("title", collectionName)
                                        put("subtitle", artistName)
                                        put("image", item.optString("artworkUrl100", "").replace("100x100bb", "600x600bb"))
                                        val relDate = item.optString("releaseDate", "")
                                        val year = if (relDate.length >= 4) relDate.substring(0, 4) else ""
                                        put("year", year)
                                    }
                                    albumsArr.put(albObj)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {}
            if (tracksArr.length() >= 25) break
        }

        // 3. Search SoundCloud if fewer than 5 tracks found
        if (tracksArr.length() < 5) {
            try {
                val encoded = URLEncoder.encode(cleanQ, "UTF-8")
                val url = "https://api-v2.soundcloud.com/search/tracks?q=$encoded&client_id=$SC_CLIENT_ID&limit=10"
                val req = Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0")
                    .build()

                httpClient.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        val bodyStr = resp.body?.string() ?: ""
                        val root = JSONObject(bodyStr)
                        val coll = root.optJSONArray("collection")
                        if (coll != null) {
                            for (i in 0 until coll.length()) {
                                val t = coll.getJSONObject(i)
                                val userObj = t.optJSONObject("user")
                                val tObj = JSONObject().apply {
                                    put("id", "sc_${t.optString("id", "")}")
                                    put("title", t.optString("title", cleanQ))
                                    put("artist", userObj?.optString("username", "Artist") ?: "Artist")
                                    put("album", "Single")
                                    put("artwork", t.optString("artwork_url", "").replace("large.jpg", "t500x500.jpg"))
                                    put("previewUrl", "")
                                    put("durationMs", t.optLong("duration", 215000L))
                                    put("releaseDate", t.optString("created_at", ""))
                                }
                                addTrack(tObj)
                            }
                        }
                    }
                }
            } catch (e: Exception) {}
        }

        val resultJson = JSONObject().apply {
            put("success", true)
            put("tracks", tracksArr)
            put("artists", artistsArr)
            put("albums", albumsArr)
        }.toString()

        searchCache[cacheKey] = resultJson
        return resultJson
    }

    fun resolveFullSong(query: String): String {
        val cleanQ = query.trim()
        val cacheKey = cleanQ.lowercase()
        fullSongCache[cacheKey]?.let { return it }

        val variants = extractVariants(cleanQ)
        val isCjk = hasCJK(cleanQ)

        // Tier 1: JioSaavn 320kbps (Prefer Latin/English variants if query has CJK)
        val saavnVariants = variants.sortedBy { if (hasCJK(it) && !it.any { c -> c in 'a'..'z' || c in 'A'..'Z' }) 1 else 0 }
        for (v in saavnVariants) {
            if (hasCJK(v) && !v.any { c -> c in 'a'..'z' || c in 'A'..'Z' || c in '0'..'9' }) continue
            try {
                val encodedQuery = URLEncoder.encode(v, "UTF-8")
                val url = "https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=5&p=1&q=$encodedQuery"
                val req = Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .build()

                httpClient.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        val bodyStr = resp.body?.string() ?: ""
                        val root = JSONObject(bodyStr)
                        val results = root.optJSONArray("results")
                        if (results != null && results.length() > 0) {
                            var selected = results.getJSONObject(0)
                            for (i in 0 until results.length()) {
                                val cand = results.getJSONObject(i)
                                if (cand.optString("song", "").trim().equals(v.trim(), ignoreCase = true)) {
                                    selected = cand
                                    break
                                }
                            }
                            val encUrl = selected.optString("encrypted_media_url", "")
                            val streamUrl = decryptSaavnMediaUrl(encUrl)
                            if (streamUrl != null) {
                                val duration = selected.optString("duration", "240").toIntOrNull() ?: 240
                                val artwork = selected.optString("image", "").replace("150x150", "500x500")
                                val payload = JSONObject().apply {
                                    put("success", true)
                                    put("provider", "jiosaavn-320k")
                                    put("title", cleanText(selected.optString("song", cleanQ)))
                                    put("artist", cleanText(selected.optString("primary_artists", selected.optString("singers", "Unknown"))))
                                    put("album", cleanText(selected.optString("album", "")))
                                    put("duration", duration)
                                    put("artwork", artwork)
                                    put("streamUrl", streamUrl)
                                }.toString()
                                fullSongCache[cacheKey] = payload
                                return payload
                            }
                        }
                    }
                }
            } catch (e: Exception) {}
        }

        // Tier 2: Apple Music / iTunes Storefronts (IN, JP, US)
        val countries = if (isCjk) listOf("in", "jp", "us") else listOf("in", "us", "jp")
        for (country in countries) {
            for (v in variants) {
                try {
                    val encodedQuery = URLEncoder.encode(v, "UTF-8")
                    val itunesUrl = "https://itunes.apple.com/search?term=$encodedQuery&country=$country&media=music&entity=song&limit=5"
                    val req = Request.Builder().url(itunesUrl).build()

                    httpClient.newCall(req).execute().use { resp ->
                        if (resp.isSuccessful) {
                            val bodyStr = resp.body?.string() ?: ""
                            val root = JSONObject(bodyStr)
                            val results = root.optJSONArray("results")
                            if (results != null && results.length() > 0) {
                                for (i in 0 until results.length()) {
                                    val item = results.getJSONObject(i)
                                    val pUrl = item.optString("previewUrl", "")
                                    if (pUrl.isNotBlank()) {
                                        val durMs = item.optLong("trackTimeMillis", 210000L)
                                        val art = item.optString("artworkUrl100", "").replace("100x100bb", "600x600bb")
                                        val payload = JSONObject().apply {
                                            put("success", true)
                                            put("provider", "itunes-aac-$country")
                                            put("title", item.optString("trackName", cleanQ))
                                            put("artist", item.optString("artistName", "Artist"))
                                            put("album", item.optString("collectionName", "Single"))
                                            put("duration", (durMs / 1000).toInt())
                                            put("artwork", art)
                                            put("streamUrl", pUrl)
                                        }.toString()
                                        fullSongCache[cacheKey] = payload
                                        return payload
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {}
            }
        }

        // Tier 3: SoundCloud Global Stream (Vietnamese, Asian, Indie, Electronic)
        for (v in variants) {
            try {
                val encodedQuery = URLEncoder.encode(v, "UTF-8")
                val scUrl = "https://api-v2.soundcloud.com/search/tracks?q=$encodedQuery&client_id=$SC_CLIENT_ID&limit=3"
                val req = Request.Builder()
                    .url(scUrl)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .build()

                httpClient.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        val bodyStr = resp.body?.string() ?: ""
                        val root = JSONObject(bodyStr)
                        val coll = root.optJSONArray("collection")
                        if (coll != null && coll.length() > 0) {
                            val track = coll.getJSONObject(0)
                            val media = track.optJSONObject("media")
                            val transList = media?.optJSONArray("transcodings")
                            if (transList != null && transList.length() > 0) {
                                var transObj = transList.getJSONObject(0)
                                for (i in 0 until transList.length()) {
                                    val t = transList.getJSONObject(i)
                                    val format = t.optJSONObject("format")
                                    if (format?.optString("protocol") == "progressive") {
                                        transObj = t
                                        break
                                    }
                                }
                                val transUrl = transObj.optString("url", "")
                                if (transUrl.isNotBlank()) {
                                    val streamReq = Request.Builder()
                                        .url("$transUrl?client_id=$SC_CLIENT_ID")
                                        .build()
                                    httpClient.newCall(streamReq).execute().use { sResp ->
                                        if (sResp.isSuccessful) {
                                            val sBody = sResp.body?.string() ?: ""
                                            val sObj = JSONObject(sBody)
                                            val directStreamUrl = sObj.optString("url", "")
                                            if (directStreamUrl.isNotBlank()) {
                                                val durMs = track.optLong("duration", 210000L)
                                                val art = track.optString("artwork_url", "").replace("large.jpg", "t500x500.jpg")
                                                val userObj = track.optJSONObject("user")
                                                val artistName = userObj?.optString("username", "Artist") ?: "Artist"
                                                val payload = JSONObject().apply {
                                                    put("success", true)
                                                    put("provider", "soundcloud-full")
                                                    put("title", track.optString("title", cleanQ))
                                                    put("artist", artistName)
                                                    put("album", "Single")
                                                    put("duration", (durMs / 1000).toInt())
                                                    put("artwork", art)
                                                    put("streamUrl", directStreamUrl)
                                                }.toString()
                                                fullSongCache[cacheKey] = payload
                                                return payload
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {}
        }

        return """{"success":false,"error":"Song not found"}"""
    }

    private fun decryptSaavnMediaUrl(encUrl: String): String? {
        return try {
            val keyBytes = "383465913834659138346591".toByteArray(Charsets.UTF_8)
            val keySpec = SecretKeySpec(keyBytes, "DESede")
            val cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val decoded = Base64.decode(encUrl, Base64.DEFAULT)
            val decrypted = cipher.doFinal(decoded)
            val url = String(decrypted, Charsets.UTF_8)
            url.replace("_96.mp4", "_320.mp4")
        } catch (e: Exception) {
            null
        }
    }

    fun resolveLyrics(track: String, artist: String): String {
        val cleanTrack = track.replace(Regex("""\s*\(From "[^"]*"\)""", RegexOption.IGNORE_CASE), "")
            .replace(Regex("""\s*\(feat\.[^)]*\)""", RegexOption.IGNORE_CASE), "")
            .replace(Regex("""\s*\[[^\]]*\]""", RegexOption.IGNORE_CASE), "")
            .trim()
        val cleanArtist = artist.split(",")[0].trim()
        val cacheKey = "${cleanTrack.lowercase()}___${cleanArtist.lowercase()}"
        lyricsCache[cacheKey]?.let { return it }

        // Strategy 1: Exact GET by track_name & artist_name
        try {
            val encTrack = URLEncoder.encode(cleanTrack, "UTF-8")
            val encArtist = URLEncoder.encode(cleanArtist, "UTF-8")
            val url = "https://lrclib.net/api/get?track_name=$encTrack&artist_name=$encArtist"
            val req = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 SONiC Music/2.0")
                .build()

            httpClient.newCall(req).execute().use { resp ->
                if (resp.isSuccessful) {
                    val bodyStr = resp.body?.string() ?: ""
                    val root = JSONObject(bodyStr)
                    val synced = root.optString("syncedLyrics", "")
                    val plain = root.optString("plainLyrics", "")
                    if (synced.isNotBlank() || plain.isNotBlank()) {
                        val linesArr = parseLrc(synced, plain)
                        if (linesArr.length() > 0) {
                            val payload = JSONObject().apply {
                                put("success", true)
                                put("synced", synced.isNotBlank())
                                put("lines", linesArr)
                                put("plain", plain)
                            }.toString()
                            lyricsCache[cacheKey] = payload
                            return payload
                        }
                    }
                }
            }
        } catch (e: Exception) {}

        // Strategy 2: LRCLIB Search by "Track Artist"
        try {
            val encQuery = URLEncoder.encode("$cleanTrack $cleanArtist", "UTF-8")
            val searchUrl = "https://lrclib.net/api/search?q=$encQuery"
            val req = Request.Builder()
                .url(searchUrl)
                .header("User-Agent", "Mozilla/5.0 SONiC Music/2.0")
                .build()

            httpClient.newCall(req).execute().use { resp ->
                if (resp.isSuccessful) {
                    val bodyStr = resp.body?.string() ?: ""
                    val arr = JSONArray(bodyStr)
                    for (i in 0 until arr.length()) {
                        val root = arr.getJSONObject(i)
                        val synced = root.optString("syncedLyrics", "")
                        val plain = root.optString("plainLyrics", "")
                        if (synced.isNotBlank() || plain.isNotBlank()) {
                            val linesArr = parseLrc(synced, plain)
                            if (linesArr.length() > 0) {
                                val payload = JSONObject().apply {
                                    put("success", true)
                                    put("synced", synced.isNotBlank())
                                    put("lines", linesArr)
                                    put("plain", plain)
                                }.toString()
                                lyricsCache[cacheKey] = payload
                                return payload
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {}

        // Strategy 3: LRCLIB Search by Track Name Only
        try {
            val encTrack = URLEncoder.encode(cleanTrack, "UTF-8")
            val searchUrl = "https://lrclib.net/api/search?track_name=$encTrack"
            val req = Request.Builder()
                .url(searchUrl)
                .header("User-Agent", "Mozilla/5.0 SONiC Music/2.0")
                .build()

            httpClient.newCall(req).execute().use { resp ->
                if (resp.isSuccessful) {
                    val bodyStr = resp.body?.string() ?: ""
                    val arr = JSONArray(bodyStr)
                    for (i in 0 until arr.length()) {
                        val root = arr.getJSONObject(i)
                        val synced = root.optString("syncedLyrics", "")
                        val plain = root.optString("plainLyrics", "")
                        if (synced.isNotBlank() || plain.isNotBlank()) {
                            val linesArr = parseLrc(synced, plain)
                            if (linesArr.length() > 0) {
                                val payload = JSONObject().apply {
                                    put("success", true)
                                    put("synced", synced.isNotBlank())
                                    put("lines", linesArr)
                                    put("plain", plain)
                                }.toString()
                                lyricsCache[cacheKey] = payload
                                return payload
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {}

        return """{"success":false,"error":"Lyrics not found"}"""
    }

    private fun parseLrc(lrcText: String, plainText: String = ""): JSONArray {
        val arr = JSONArray()
        if (lrcText.isNotBlank()) {
            val lines = lrcText.split("\n")
            val regex = Regex("""\[(\d{2}):(\d{2})\.(\d{2,3})\](.*)""")

            for (line in lines) {
                val match = regex.find(line.trim())
                if (match != null) {
                    val mins = match.groupValues[1].toDoubleOrNull() ?: 0.0
                    val secs = match.groupValues[2].toDoubleOrNull() ?: 0.0
                    val msStr = match.groupValues[3].padEnd(3, '0').take(3)
                    val ms = msStr.toDoubleOrNull() ?: 0.0
                    val time = mins * 60 + secs + ms / 1000.0
                    val text = match.groupValues[4].trim()
                    if (text.isNotBlank()) {
                        val item = JSONObject().apply {
                            put("time", time)
                            put("text", text)
                        }
                        arr.put(item)
                    }
                }
            }
        }

        // If no synced lines parsed, convert plain text lines
        if (arr.length() == 0 && plainText.isNotBlank()) {
            val pLines = plainText.split("\n").map { it.trim() }.filter { it.isNotBlank() }
            val interval = if (pLines.isNotEmpty()) 210.0 / pLines.size else 4.0
            pLines.forEachIndexed { idx, txt ->
                val obj = JSONObject().apply {
                    put("time", idx * interval)
                    put("text", txt)
                }
                arr.put(obj)
            }
        }

        return arr
    }

    fun getLiveNewReleases(): String {
        val url = "https://www.jiosaavn.com/api.php?__call=search.getResults&_format=json&n=25&p=1&q=Latest%20Hindi%20Songs"
        return try {
            val req = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0")
                .build()
            httpClient.newCall(req).execute().use { resp ->
                val body = resp.body?.string() ?: "{}"
                val json = JSONObject(body)
                val results = json.optJSONArray("results") ?: JSONArray()
                val list = JSONArray()
                for (i in 0 until results.length()) {
                    val s = results.getJSONObject(i)
                    val rawImg = s.optString("image", "").replace("150x150", "500x500")
                    val item = JSONObject().apply {
                        put("id", "live_${s.optString("id", "")}")
                        put("title", s.optString("song", "New Release").replace("&quot;", "\"").replace("&amp;", "&"))
                        put("artist", s.optString("singers", "Various Artists").replace("&quot;", "\"").replace("&amp;", "&"))
                        put("album", s.optString("album", "Single").replace("&quot;", "\"").replace("&amp;", "&"))
                        put("image", rawImg)
                        put("year", s.optString("year", "2026"))
                        put("query", s.optString("song", "") + " " + s.optString("singers", ""))
                        put("provider", "jiosaavn-live")
                    }
                    list.put(item)
                }
                JSONObject().put("status", "success").put("data", list).toString()
            }
        } catch (e: Exception) {
            "{\"status\":\"error\",\"data\":[]}"
        }
    }
}
