package com.densis.musicplayer.playlist.presentation.store

sealed class PlaylistEffect {
    data object OpenPlayer : PlaylistEffect()
    data class LoadTrackCover(val id: String) : PlaylistEffect()
}