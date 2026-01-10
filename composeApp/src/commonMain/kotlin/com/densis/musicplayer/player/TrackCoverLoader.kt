package com.densis.musicplayer.player

import androidx.compose.ui.graphics.ImageBitmap

expect class TrackCoverLoader {
    suspend fun load(artworkKey: String): ImageBitmap?
}