package com.densis.musicplayer.player.presentation.store

import androidx.compose.ui.graphics.ImageBitmap
import com.densis.musicplayer.domain.entity.Track

interface PlayerEvent

sealed class PlayerEventUi : PlayerEvent {
    object InitScreen : PlayerEventUi()
    object OnPreviousButtonClicked : PlayerEventUi()
    object OnNextButtonClicked : PlayerEventUi()
    object OnPlayPauseButtonClicked : PlayerEventUi()
    data class OnSeekTo(val seekTo: Float) : PlayerEventUi()
}

sealed class PlayerEventInternal : PlayerEvent {
    data class OnReceivedCurrentTrack(val track: Track?) : PlayerEventInternal()
    data class OnTrackCoverLoaded(val trackCover: ImageBitmap?) : PlayerEventInternal()
    data class OnSeekPositionUpdated(val position: Float) : PlayerEventInternal()
    data class OnPlayPauseStateUpdated(val isPlaying: Boolean) : PlayerEventInternal()
    data class OnReceivedTotalDuration(val duration: Float) : PlayerEventInternal()
}