package com.densis.musicplayer.playlist.presentation.store

sealed class PlaylistEffect {
    data object OpenPlayer : PlaylistEffect()
}