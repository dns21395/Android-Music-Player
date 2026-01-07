package com.densis.musicplayer.player.presentation.store

sealed class PlayerCommand {
    object GetTrack : PlayerCommand()
    object PlayPreviousTrack : PlayerCommand()
    object PlayerNextTrack : PlayerCommand()
    data class PlayOrPause(val isPlaying: Boolean) : PlayerCommand()
}