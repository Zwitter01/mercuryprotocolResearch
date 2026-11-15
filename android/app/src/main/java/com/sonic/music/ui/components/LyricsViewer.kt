package com.sonic.music.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonic.music.model.LyricLine
import com.sonic.music.ui.theme.SonicInk1
import com.sonic.music.ui.theme.SonicMagenta
import com.sonic.music.ui.theme.SonicTextBase
import com.sonic.music.ui.theme.SonicTextSubdued

@Composable
fun LyricsViewer(
    lyrics: List<LyricLine>,
    currentPositionMs: Long,
    onSeekTo: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSeconds = currentPositionMs / 1000.0
    val activeIndex = remember(lyrics, currentSeconds) {
        lyrics.indexOfLast { it.timeSeconds <= currentSeconds }.coerceAtLeast(0)
    }

    val listState = rememberLazyListState()

    LaunchedEffect(activeIndex) {
        if (activeIndex in lyrics.indices) {
            listState.animateScrollToItem(activeIndex)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(SonicInk1)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        if (lyrics.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No lyrics available for this song",
                    color = SonicTextSubdued,
                    fontSize = 15.sp
                )
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(18.dp),
                contentPadding = PaddingValues(vertical = 40.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(lyrics) { index, line ->
                    val isActive = index == activeIndex
                    val isPast = index < activeIndex

                    val textColor = when {
                        isActive -> Color.White
                        isPast -> SonicTextSubdued.copy(alpha = 0.5f)
                        else -> SonicTextSubdued.copy(alpha = 0.25f)
                    }

                    val fontSize = if (isActive) 24.sp else 20.sp
                    val fontWeight = if (isActive) FontWeight.ExtraBold else FontWeight.Bold

                    Text(
                        text = line.text,
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        color = textColor,
                        lineHeight = 32.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSeekTo((line.timeSeconds * 1000).toLong())
                            }
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
