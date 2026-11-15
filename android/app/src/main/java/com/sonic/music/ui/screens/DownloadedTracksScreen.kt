package com.sonic.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.sonic.music.data.local.DownloadedTrack
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

@Composable
fun DownloadedTracksScreen(
    downloadedTracks: List<DownloadedTrack>,
    currentTrack: Track?,
    isPlaying: Boolean,
    onTrackSelect: (Track) -> Unit,
    onRemoveDownload: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSizeMb = downloadedTracks.sumOf { it.fileSizeBytes } / (1024.0 * 1024.0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
    ) {
        // Top Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = SonicTextBase
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Downloaded Songs",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SonicTextBase
                )
                Text(
                    text = "${downloadedTracks.size} songs • %.1f MB".format(totalSizeMb),
                    fontSize = 12.sp,
                    color = SonicTextSubdued
                )
            }
        }

        if (downloadedTracks.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No downloaded songs yet",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SonicTextBase
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap the download button on any song or in the player to listen offline without internet.",
                        fontSize = 14.sp,
                        color = SonicTextSubdued,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(downloadedTracks, key = { it.id }) { item ->
                    val domainTrack = item.toTrack()
                    val isCurrent = currentTrack?.id == item.id
                    val sizeMb = item.fileSizeBytes / (1024.0 * 1024.0)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isCurrent) SonicInk2 else Color.Transparent)
                            .clickable { onTrackSelect(domainTrack) }
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        AsyncImage(
                            model = domainTrack.artworkUrl,
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isCurrent) SonicMagenta else SonicTextBase,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Downloaded",
                                    tint = Color(0xFF1ED760),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${item.artist} • %.1f MB".format(sizeMb),
                                    fontSize = 12.sp,
                                    color = SonicTextSubdued,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        IconButton(onClick = { onRemoveDownload(item.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete download",
                                tint = SonicTextSubdued,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
