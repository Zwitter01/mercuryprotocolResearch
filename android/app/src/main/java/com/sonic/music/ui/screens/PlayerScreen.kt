package com.sonic.music.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sonic.music.model.LyricLine
import com.sonic.music.model.Track
import com.sonic.music.ui.components.LyricsViewer
import com.sonic.music.ui.theme.*

@Composable
fun PlayerScreen(
    currentTrack: Track?,
    isPlaying: Boolean,
    currentPositionMs: Long,
    durationMs: Long,
    isShuffle: Boolean,
    repeatMode: Int,
    isLyricsOpen: Boolean,
    lyrics: List<LyricLine>,
    isDownloaded: Boolean = false,
    downloadProgress: Int? = null,
    isConnectedToNetwork: Boolean = false,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onToggleShuffle: () -> Unit,
    onToggleRepeat: () -> Unit,
    onToggleLike: (String) -> Unit,
    onToggleLyrics: () -> Unit,
    onToggleDownload: () -> Unit = {},
    onOpenConnect: () -> Unit = {},
    onOpenQueue: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentTrack == null) return

    var isUserScrubbing by remember { mutableStateOf(false) }
    var scrubPositionMs by remember { mutableStateOf(0f) }

    val safeDurationMs = if (durationMs > 0L) durationMs else 30000L
    val effectivePositionMs = if (isUserScrubbing) scrubPositionMs.toLong() else currentPositionMs

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        // Top Bar (Collapse Down Arrow + Context Title + More)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Collapse Player",
                    tint = SonicTextBase,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "PLAYING FROM PLAYLIST",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = SonicTextSubdued
                )
                Text(
                    text = currentTrack.album,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SonicTextBase,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = { /* More actions */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = SonicTextBase
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Center Content: Either Large Artwork or Synchronized Lyrics View
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            if (isLyricsOpen) {
                LyricsViewer(
                    lyrics = lyrics,
                    currentPositionMs = effectivePositionMs,
                    onSeekTo = onSeekTo,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AsyncImage(
                    model = currentTrack.artworkUrl,
                    contentDescription = currentTrack.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .shadow(24.dp, RoundedCornerShape(16.dp))
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Track Info Row (Title, Artist & Like Heart)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = currentTrack.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SonicTextBase,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentTrack.artist,
                    fontSize = 15.sp,
                    color = SonicTextSubdued,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = { onToggleLike(currentTrack.id) }) {
                Icon(
                    imageVector = if (currentTrack.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like Song",
                    tint = if (currentTrack.isLiked) SonicMagenta else SonicTextBase,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Scrubber / Seek Bar (Slider)
        Slider(
            value = (effectivePositionMs.toFloat() / safeDurationMs.toFloat()).coerceIn(0f, 1f),
            onValueChange = { ratio ->
                isUserScrubbing = true
                scrubPositionMs = ratio * safeDurationMs
            },
            onValueChangeFinished = {
                onSeekTo(scrubPositionMs.toLong())
                isUserScrubbing = false
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = SonicMagenta,
                inactiveTrackColor = SonicLine
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Time labels (Elapsed vs Total)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = formatDuration(effectivePositionMs),
                fontSize = 12.sp,
                color = SonicTextSubdued
            )
            Text(
                text = formatDuration(safeDurationMs),
                fontSize = 12.sp,
                color = SonicTextSubdued
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Primary Controls Row (Shuffle, Prev, Play/Pause, Next, Repeat)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Shuffle
            IconButton(onClick = onToggleShuffle) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = if (isShuffle) SonicMagenta else SonicTextSubdued,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Previous
            IconButton(onClick = onPrevClick) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous Track",
                    tint = SonicTextBase,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Play / Pause (Large Circular Button)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onPlayPauseClick)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.Black,
                    modifier = Modifier.size(38.dp)
                )
            }

            // Next
            IconButton(onClick = onNextClick) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next Track",
                    tint = SonicTextBase,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Repeat (Off / All / One)
            IconButton(onClick = onToggleRepeat) {
                Icon(
                    imageVector = if (repeatMode == 2) Icons.Default.RepeatOne else Icons.Default.Repeat,
                    contentDescription = "Repeat",
                    tint = if (repeatMode > 0) SonicMagenta else SonicTextSubdued,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Utilities (Device connect, Download, Lyrics Toggle, Queue)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            IconButton(onClick = onOpenConnect) {
                Icon(
                    imageVector = Icons.Default.SpeakerGroup,
                    contentDescription = "Connect Devices",
                    tint = if (isConnectedToNetwork) SonicCyan else SonicTextSubdued,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Download Offline Button
            IconButton(onClick = onToggleDownload) {
                if (downloadProgress != null) {
                    CircularProgressIndicator(
                        progress = { downloadProgress / 100f },
                        color = SonicCyan,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else if (isDownloaded) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Downloaded",
                        tint = Color(0xFF1ED760),
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.DownloadForOffline,
                        contentDescription = "Download Offline",
                        tint = SonicTextSubdued,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Lyrics Pill Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isLyricsOpen) SonicMagenta else SonicInk2)
                    .clickable(onClick = onToggleLyrics)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Lyrics",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isLyricsOpen) Color.White else SonicTextBase
                )
            }

            // Queue Button
            IconButton(onClick = onOpenQueue) {
                Icon(
                    imageVector = Icons.Default.QueueMusic,
                    contentDescription = "Open Queue",
                    tint = SonicTextSubdued,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

// Safe format helper without crashes
fun formatDuration(durationMs: Long): String {
    if (durationMs <= 0L) return "0:00"
    val totalSeconds = (durationMs / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}
