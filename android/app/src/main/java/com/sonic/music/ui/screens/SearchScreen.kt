package com.sonic.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

data class GenreCategory(
    val name: String,
    val query: String,
    val gradient: Brush
)

@Composable
fun SearchScreen(
    searchQuery: String,
    isSearching: Boolean,
    searchResults: List<Track>,
    currentTrack: Track?,
    isPlaying: Boolean,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onTrackSelect: (Track) -> Unit,
    onToggleLike: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        GenreCategory("Bollywood", "Bollywood Hits", Brush.linearGradient(listOf(Color(0xFFE1306C), Color(0xFFF77737)))),
        GenreCategory("Punjabi", "Punjabi Pop", Brush.linearGradient(listOf(Color(0xFFFF8008), Color(0xFFFFC837)))),
        GenreCategory("Pop", "Global Pop", Brush.linearGradient(listOf(Color(0xFF8A2387), Color(0xFFE94057)))),
        GenreCategory("Hip-Hop", "Indian Hip Hop", Brush.linearGradient(listOf(Color(0xFF0F2027), Color(0xFF2C5364)))),
        GenreCategory("Romance", "Romantic Melodies", Brush.linearGradient(listOf(Color(0xFFFF5F6D), Color(0xFFFFC371)))),
        GenreCategory("Party", "Party Dance", Brush.linearGradient(listOf(Color(0xFF11998E), Color(0xFF38EF7D)))),
        GenreCategory("Chill", "Chill Lofi", Brush.linearGradient(listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2)))),
        GenreCategory("Retro", "Classic Retro Bollywood", Brush.linearGradient(listOf(Color(0xFFF37335), Color(0xFFFDC830))))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SonicInk0)
            .statusBarsPadding()
    ) {
        // Search Header Title
        Text(
            text = "Search",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SonicTextBase,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Spotify Search Bar Capsule
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Lens",
                    tint = Color.Black,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "What do you want to play?",
                            color = Color(0xFF757575),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onQueryChange,
                        singleLine = true,
                        cursorBrush = SolidColor(Color.Black),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(onClick = onClearQuery)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content: Category Grid or Live Search Results
        if (searchQuery.isEmpty()) {
            // Browse all categories
            Text(
                text = "Browse all",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = SonicTextBase,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categories) { cat ->
                    Box(
                        modifier = Modifier
                            .height(96.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(cat.gradient)
                            .clickable { onQueryChange(cat.query) }
                            .padding(14.dp)
                    ) {
                        Text(
                            text = cat.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }
                }
            }
        } else {
            // Live Search Results
            if (isSearching) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    CircularProgressIndicator(
                        color = SonicMagenta,
                        modifier = Modifier.size(36.dp)
                    )
                }
            } else if (searchResults.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)
                ) {
                    Text(
                        text = "No songs found for \"$searchQuery\"",
                        color = SonicTextSubdued,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 100.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Top Result Card (First Match)
                    item {
                        val top = searchResults.first()
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Top result",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SonicTextBase,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SonicInk2)
                                    .clickable { onTrackSelect(top) }
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AsyncImage(
                                        model = top.artworkUrl,
                                        contentDescription = top.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = top.title,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SonicTextBase,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = top.artist,
                                            fontSize = 14.sp,
                                            color = SonicTextSubdued,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Song",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(SonicInk3)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(SonicMagenta)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play Top Song",
                                            tint = Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "Songs",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SonicTextBase
                            )
                        }
                    }

                    // Songs List
                    items(searchResults.drop(1), key = { it.id }) { track ->
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
    }
}
