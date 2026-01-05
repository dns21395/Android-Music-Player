package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.domain.entity.Track

interface PlayerEvent {


}

sealed class PlayerEventUi : PlayerEvent {
    object InitScreen : PlayerEventUi()
}

sealed class PlayerEventInternal : PlayerEvent {
    data class OnReceivedCurrentTrack(val track: Track?) : PlayerEventInternal()
}