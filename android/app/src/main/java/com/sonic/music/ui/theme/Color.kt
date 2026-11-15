package com.sonic.music.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// SONiC "Aurora Noir" Palette (Matches Web Player Design System 1:1)
val SonicCoral = Color(0xFFFF7A45)
val SonicMagenta = Color(0xFFFF3D8B)
val SonicIndigo = Color(0xFF7C5CFF)
val SonicCyan = Color(0xFF00F5D4)

val SonicInk0 = Color(0xFF050409) // Deep canvas
val SonicInk1 = Color(0xFF0B0A12) // Card background
val SonicInk2 = Color(0xFF13111C) // Elevated surface
val SonicInk3 = Color(0xFF1C1928) // Borders and highlights

val SonicTextBase = Color(0xFFF6F3FB)
val SonicTextSubdued = Color(0xFF9A95AD)
val SonicLine = Color(0x14FFFFFF)
val SonicBorderSubtle = Color(0x14FFFFFF)

val SonicAuroraGradient = Brush.linearGradient(
    colors = listOf(SonicCoral, SonicMagenta, SonicIndigo)
)
