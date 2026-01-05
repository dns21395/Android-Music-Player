package com.densis.musicplayer.player.presentation.store

import money.vivid.elmslie.core.store.StateReducer

val PlayerReducer =
    object : StateReducer<PlayerEvent, PlayerState, PlayerEffect, PlayerCommand>() {
        override fun StateReducer<PlayerEvent, PlayerState, PlayerEffect, PlayerCommand>.Result.reduce(
            event: PlayerEvent
        ) {
            when (event) {
                PlayerEventUi.InitScreen -> {
                    commands { +PlayerCommand.GetTrack }
                }

                is PlayerEventInternal.OnReceivedCurrentTrack -> {
                    val track = event.track
                    state {
                        copy(
                            name = track?.title ?: "",
                            artist = track?.artist ?: "",
                        )
                    }
                }
            }
        }
    }