package com.sonic.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

@Composable
fun HomeScreen(
    tracks: List<Track>,
    currentTrack: Track?,
    isPlaying: Boolean,
    onTrackSelect: (Track) -> Unit,
    onToggleLike: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMood by remember { mutableStateOf("auto") }
    val moods = listOf(
        "auto" to "Auto-Mood",
        "romantic" to "Romantic",
        "punjabi" to "Punjabi",
        "party" to "Party",
        "sad" to "Sad",
        "chill" to "Chill",
        "retro" to "Retro"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
    ) {
        // Top Header with SONiC Logo & Brand
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(SonicAuroraGradient)
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "SONiC Logo",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "SONiC",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
                color = SonicTextBase
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "100% Free",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SonicCyan,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(SonicInk2)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        // Horizontal Mood Pills Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moods.forEach { (key, label) ->
                val isSelected = selectedMood == key
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) SonicMagenta else SonicInk2)
                        .clickable { selectedMood = key }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else SonicTextSubdued
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Shelf Header
        Text(
            text = "Trending Now",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = SonicTextBase,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Tracks List
        LazyColumn(
            contentPadding = PaddingValues(bottom = 90.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tracks, key = { it.id }) { track ->
                TrackRowItem(
                    track = track,
                    isCurrent = currentTrack?.id == track.id,
                    isPlaying = isPlaying && currentTrack?.id == track.id,
                    onClick = { onTrackSelect(track) },
                    onLikeClick = { onToggleLike(track) }
                )
            }
        }
    }
}

@Composable
fun TrackRowItem(
    track: Track,
    isCurrent: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isCurrent) SonicInk2 else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = track.artworkUrl,
            contentDescription = track.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isCurrent) SonicMagenta else SonicTextBase,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = track.artist,
                fontSize = 13.sp,
                color = SonicTextSubdued,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = if (track.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = if (track.isLiked) SonicMagenta else SonicTextSubdued,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
