package com.sonic.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SpeakerGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

@Composable
fun LibraryScreen(
    onOpenLikedSongs: () -> Unit,
    onOpenDownloads: () -> Unit,
    onOpenConnect: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
    ) {
        // Library Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SonicMagenta)
            ) {
                Text(
                    text = "V",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Your Library",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SonicTextBase
            )
        }

        // Library Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(SonicInk2)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Playlists",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = SonicTextBase
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(SonicInk2)
                    .clickable(onClick = onOpenDownloads)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Downloaded",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = SonicCyan
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(SonicInk2)
                    .clickable(onClick = onOpenConnect)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Connect",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = SonicMagenta
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Pinned Liked Songs Card (Permanent Spotify Card)
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onOpenLikedSongs)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF450AF5), Color(0xFFC4EFD9))
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Liked Songs",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Liked Songs",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SonicTextBase
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Pinned • Playlist",
                            fontSize = 13.sp,
                            color = SonicTextSubdued
                        )
                    }
                }
            }

            // Pre-installed Vault / Offline Songs
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onOpenDownloads)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SonicInk2)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "SONiC Vault",
                            tint = SonicCyan,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "SONiC Offline Vault",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SonicTextBase
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Local Storage • No Internet Required",
                            fontSize = 13.sp,
                            color = SonicCyan
                        )
                    }
                }
            }

            // SONiC Connect Multi-Device Link
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onOpenConnect)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(SonicIndigo, SonicMagenta)
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SpeakerGroup,
                            contentDescription = "SONiC Connect",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "SONiC Connect",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SonicTextBase
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Sync & control PC, Laptop, and Web Player",
                            fontSize = 13.sp,
                            color = SonicTextSubdued
                        )
                    }
                }
            }

            // Custom User Playlists
            item {
                PlaylistRow(
                    title = "Discover Weekly",
                    subtitle = "Updated every Monday • 30 tracks"
                )
            }

            item {
                PlaylistRow(
                    title = "Daily Mix 1",
                    subtitle = "Arijit Singh, Pritam and more"
                )
            }

            item {
                PlaylistRow(
                    title = "Chill Out Beats",
                    subtitle = "Ambient and lo-fi tunes"
                )
            }
        }
    }
}

@Composable
private fun PlaylistRow(
    title: String,
    subtitle: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SonicInk2)
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = title,
                tint = SonicTextSubdued,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = SonicTextBase
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = SonicTextSubdued
            )
        }
    }
}
