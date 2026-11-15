package com.sonic.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

@Composable
fun QueueScreen(
    currentTrack: Track?,
    queue: List<Track>,
    onTrackSelect: (Track) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Queue",
                    tint = SonicTextBase
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Queue",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SonicTextBase
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Section 1: Now Playing
            item {
                Text(
                    text = "Now playing",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SonicTextSubdued,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (currentTrack != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SonicInk2)
                            .padding(10.dp)
                    ) {
                        AsyncImage(
                            model = currentTrack.artworkUrl,
                            contentDescription = currentTrack.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currentTrack.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = SonicMagenta,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = currentTrack.artist,
                                fontSize = 13.sp,
                                color = SonicTextSubdued,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Next in queue",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SonicTextSubdued,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Section 2: Remaining Tracks in Queue
            val upcomingTracks = queue.filter { it.id != currentTrack?.id }
            if (upcomingTracks.isEmpty()) {
                item {
                    Text(
                        text = "No more tracks in queue",
                        color = SonicTextSubdued,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            } else {
                itemsIndexed(upcomingTracks) { index, track ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTrackSelect(track) }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontSize = 13.sp,
                            color = SonicTextSubdued,
                            modifier = Modifier.width(28.dp)
                        )

                        AsyncImage(
                            model = track.artworkUrl,
                            contentDescription = track.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = track.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SonicTextBase,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = track.artist,
                                fontSize = 12.sp,
                                color = SonicTextSubdued,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
