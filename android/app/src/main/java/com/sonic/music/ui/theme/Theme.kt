package com.sonic.music.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SonicMagenta,
    secondary = SonicCoral,
    tertiary = SonicIndigo,
    background = SonicInk0,
    surface = SonicInk1,
    onPrimary = SonicTextBase,
    onSecondary = SonicTextBase,
    onTertiary = SonicTextBase,
    onBackground = SonicTextBase,
    onSurface = SonicTextBase
)

@Composable
fun SonicTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
