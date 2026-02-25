package com.densis.musicplayer.player.presentation.store

import money.vivid.elmslie.core.store.StateReducer

val PlayerReducer =
    object : StateReducer<PlayerEvent, PlayerState, PlayerEffect, PlayerCommand>() {
        override fun StateReducer<PlayerEvent, PlayerState, PlayerEffect, PlayerCommand>.Result.reduce(
            event: PlayerEvent
        ) {
            when (event) {
                PlayerEventUi.InitScreen -> {
                    commands {
                        +PlayerCommand.ObserveTrack
                        +PlayerCommand.ObserveTotalDuration
                        +PlayerCommand.ObserveCurrentPosition
                        +PlayerCommand.ObservePlayPause
                    }
                }

                PlayerEventInternal.OnSeekToUpdated -> {
                    commands {
                        +PlayerCommand.ObserveCurrentPosition
                    }
                }

                is PlayerEventInternal.OnReceivedCurrentTrack -> {
                    val track = event.track

                    if (track?.id == state.trackId) {
                        return
                    }

                    state {
                        copy(
                            trackId = track?.id ?: "",
                            name = track?.title ?: "",
                            artist = track?.artist ?: "",
                            image = null,
                            currentTime = 0f,
                        )
                    }
                    if (track?.trackCoverId != null) {
                        effects { +PlayerEffect.LoadTrackCover(track.trackCoverId) }
                    }
                }

                is PlayerEventInternal.OnTrackCoverLoaded -> {
                    effects {
                        +PlayerEffect.OnLoadedCover(event.byteArray)
                    }
                }

                is PlayerEventUi.OnPreviousButtonClicked -> {
                    state { copy(currentTime = 0f) }
                    commands {
                        +PlayerCommand.PlayPreviousTrack
                    }

                }

                is PlayerEventUi.OnNextButtonClicked -> {
                    state { copy(currentTime = 0f) }
                    commands {
                        +PlayerCommand.PlayerNextTrack
                    }
                }

                is PlayerEventUi.OnPlayPauseButtonClicked -> {
                    commands { +PlayerCommand.PlayOrPause(state.isPlaying) }
                }

                is PlayerEventInternal.OnSeekPositionUpdated -> {
                    state { copy(currentTime = event.position) }
                }

                is PlayerEventInternal.OnPlayPauseStateUpdated -> {
                    state { copy(isPlaying = event.isPlaying) }
                }

                is PlayerEventInternal.OnReceivedTotalDuration -> {
                    state { copy(totalTime = event.duration) }
                }


                is PlayerEventUi.StopDragging -> {
                    commands {
                        +PlayerCommand.SeekTo(state.currentTime)
                    }

                }

                is PlayerEventUi.StartDragging -> {
                    state { copy(currentTime = event.position) }
                    commands { +PlayerCommand.StopObserveCurrentPosition }
                }

                is PlayerEventUi.PlayTrack -> {
                    commands { +PlayerCommand.PlayTrack(event.trackId) }
                }

                is PlayerEventUi.OnBackButtonClicked -> {
                    effects { +PlayerEffect.NavigationPopBackStack }
                }
            }
        }
    }