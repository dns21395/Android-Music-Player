package com.densis.musicplayer.player.presentation.store

sealed class PlayerEffect {
    data class LoadTrackCover(val id: String) : PlayerEffect()
    data object NavigationPopBackStack : PlayerEffect()
    data class OnLoadedCover(val byteArray: ByteArray?) : PlayerEffect()
}