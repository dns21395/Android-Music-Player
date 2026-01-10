package com.densis.musicplayer.playlist.presentation.store

import androidx.compose.ui.graphics.ImageBitmap
import com.densis.musicplayer.domain.entity.Track

data class PlaylistState(
    val playlist: List<Track> = emptyList(),
    val currentTrackId: String = "",
    val currentTrackArtist: String = "",
    val currentTrackName: String = "",
    val currentTrackCover: ImageBitmap? = null,
    val isPlaying: Boolean = false,
)