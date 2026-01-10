package com.densis.musicplayer.player.presentation.store

import androidx.compose.ui.graphics.ImageBitmap

data class PlayerState(
    val name: String = "",
    val artist: String = "",
    val image: ImageBitmap? = null,
    val isPlaying: Boolean = true,
    val currentTime: Float = 0f,
    val totalTime: Float = 0f
)