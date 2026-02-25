package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.domain.entity.Track

interface PlayerEvent

sealed class PlayerEventUi : PlayerEvent {
    object InitScreen : PlayerEventUi()
    data class PlayTrack(val trackId: String) : PlayerEventUi()
    object OnPreviousButtonClicked : PlayerEventUi()
    object OnNextButtonClicked : PlayerEventUi()
    object OnPlayPauseButtonClicked : PlayerEventUi()
    data class StartDragging(val position: Float) : PlayerEventUi()
    object StopDragging : PlayerEventUi()
    data object OnBackButtonClicked : PlayerEventUi()
}

sealed class PlayerEventInternal : PlayerEvent {
    data class OnReceivedCurrentTrack(val track: Track?) : PlayerEventInternal()
    data class OnTrackCoverLoaded(val byteArray: ByteArray?) : PlayerEventInternal()
    data class OnSeekPositionUpdated(val position: Float) : PlayerEventInternal()
    data class OnPlayPauseStateUpdated(val isPlaying: Boolean) : PlayerEventInternal()
    data class OnReceivedTotalDuration(val duration: Float) : PlayerEventInternal()

    data object OnSeekToUpdated : PlayerEventInternal()
}