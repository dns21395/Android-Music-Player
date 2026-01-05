package com.densis.musicplayer.player.presentation.store

import androidx.compose.ui.graphics.ImageBitmap

data class PlayerState(
    val name: String = "",
    val artist: String = "",
    val image: ImageBitmap? = null,
)