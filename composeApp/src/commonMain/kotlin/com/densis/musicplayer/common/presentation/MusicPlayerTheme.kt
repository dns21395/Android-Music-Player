package com.densis.musicplayer.common.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MusicPlayerTheme(
    content: @Composable () -> Unit
) {
    val colors = darkColorScheme(
        background = Color(0xFF0B0B0B),
        surface = Color(0xFF121212),
        primary = Color.White,
        onPrimary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
        surfaceVariant = Color(0xFF1E1E1E),
        onSurfaceVariant = Color(0xFFBDBDBD),
    )


    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}