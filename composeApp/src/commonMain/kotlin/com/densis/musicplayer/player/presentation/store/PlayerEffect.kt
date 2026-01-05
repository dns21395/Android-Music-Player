package com.densis.musicplayer.player.presentation.store

sealed class PlayerEffect {
    data class LoadTrackCover(val id: String) : PlayerEffect()
}