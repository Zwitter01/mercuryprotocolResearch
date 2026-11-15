package com.sonic.music.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonic.music.ui.theme.*
import com.sonic.music.ui.viewmodel.AppScreen

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = SonicInk0,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        // Tab 1: Home
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onTabSelected(AppScreen.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(text = "Home", fontSize = 11.sp)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = SonicTextSubdued,
                unselectedTextColor = SonicTextSubdued,
                indicatorColor = SonicMagenta.copy(alpha = 0.35f)
            )
        )

        // Tab 2: Search
        NavigationBarItem(
            selected = currentScreen == AppScreen.SEARCH,
            onClick = { onTabSelected(AppScreen.SEARCH) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            label = {
                Text(text = "Search", fontSize = 11.sp)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = SonicTextSubdued,
                unselectedTextColor = SonicTextSubdued,
                indicatorColor = SonicMagenta.copy(alpha = 0.35f)
            )
        )

        // Tab 3: Your Library
        NavigationBarItem(
            selected = currentScreen == AppScreen.LIBRARY,
            onClick = { onTabSelected(AppScreen.LIBRARY) },
            icon = {
                Icon(
                    imageVector = Icons.Default.LibraryMusic,
                    contentDescription = "Your Library"
                )
            },
            label = {
                Text(text = "Your Library", fontSize = 11.sp)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = SonicTextSubdued,
                unselectedTextColor = SonicTextSubdued,
                indicatorColor = SonicMagenta.copy(alpha = 0.35f)
            )
        )
    }
}
