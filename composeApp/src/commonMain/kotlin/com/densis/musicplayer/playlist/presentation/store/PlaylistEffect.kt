package com.densis.musicplayer.playlist.presentation.store

sealed class PlaylistEffect {
    data class OpenPlayer(val trackId: String? = null) : PlaylistEffect()
    data class LoadTrackCover(val id: String) : PlaylistEffect()
}