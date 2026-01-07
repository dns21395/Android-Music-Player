package com.densis.musicplayer.player.presentation.store

import androidx.compose.ui.graphics.ImageBitmap
import com.densis.musicplayer.domain.entity.Track

interface PlayerEvent

sealed class PlayerEventUi : PlayerEvent {
    object InitScreen : PlayerEventUi()
    object OnPreviousButtonClicked : PlayerEventUi()
    object OnNextButtonClicked : PlayerEventUi()
    object OnPlayPauseButtonClicked : PlayerEventUi()
}

sealed class PlayerEventInternal : PlayerEvent {
    data class OnReceivedCurrentTrack(val track: Track?) : PlayerEventInternal()
    data class OnTrackCoverLoaded(val trackCover: ImageBitmap?) : PlayerEventInternal()
}