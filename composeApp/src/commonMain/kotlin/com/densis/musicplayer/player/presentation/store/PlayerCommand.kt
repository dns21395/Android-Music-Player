package com.densis.musicplayer.player.presentation.store

sealed class PlayerCommand {
    object ObserveTrack : PlayerCommand()
    object ObservePlayPause : PlayerCommand()
    object ObserveCurrentPosition : PlayerCommand()
    object ObserveTotalDuration : PlayerCommand()
    object PlayPreviousTrack : PlayerCommand()
    object PlayerNextTrack : PlayerCommand()
    data object StopObserveCurrentPosition : PlayerCommand()
    data class PlayOrPause(val isPlaying: Boolean) : PlayerCommand()
    data class SeekTo(val seekTo: Float) : PlayerCommand()
    data class PlayTrack(val trackId: String) : PlayerCommand()
}