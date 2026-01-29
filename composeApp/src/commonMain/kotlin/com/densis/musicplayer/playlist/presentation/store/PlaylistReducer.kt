package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.GetPlaylist
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.ObserveCurrentTrack
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.PlayOrPause
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.PlayTrack
import com.densis.musicplayer.playlist.presentation.store.PlaylistEffect.*
import com.densis.musicplayer.playlist.presentation.store.PlaylistEffect.LoadTrackCover
import com.densis.musicplayer.playlist.presentation.store.PlaylistEffect.OpenPlayer
import money.vivid.elmslie.core.store.StateReducer

val PlaylistReducer =
    object : StateReducer<PlaylistEvent, PlaylistState, PlaylistEffect, PlaylistCommand>() {
        override fun StateReducer<PlaylistEvent, PlaylistState, PlaylistEffect, PlaylistCommand>.Result.reduce(
            event: PlaylistEvent
        ) {
            when (event) {
                PlaylistEvent.InitScreen -> {
                    commands {
                        +GetPlaylist
                        +ObserveCurrentTrack
                        +PlaylistCommand.ObservePlayPause
                    }
                }


                is PlaylistEvent.OnReceivedPlaylist -> {
                    state { copy(playlist = event.playlist) }
                }

                is PlaylistEvent.OnTrackClicked -> {
                    commands { +PlayTrack(event.track) }
                }

                is PlaylistEvent.OpenPlayer -> {
                    effects { +OpenPlayer(event.trackId) }
                }

                is PlaylistEvent.OnReceivedCurrentTrack -> {
                    val track = event.track

                    if (track?.id == state.currentTrackId) {
                        return
                    }

                    state {
                        copy(
                            currentTrackId = track?.id ?: "",
                            currentTrackArtist = track?.artist ?: "",
                            currentTrackName = track?.title ?: "",
                            currentTrackCoverId = track?.trackCoverId,
                            isLoadedCover = false
                        )
                    }
                }

                is PlaylistEvent.OnTrackCoverLoaded -> {
                    effects {
                        +OnLoadedCover(event.trackCover)
                    }
                    state { copy(isLoadedCover = true) }
                }

                PlaylistEvent.OnPlayPauseButtonClicked -> {
                    commands { +PlayOrPause(state.isPlaying) }
                }

                is PlaylistEvent.OnPlayPauseStateUpdated -> {
                    state { copy(isPlaying = event.isPlaying) }
                }

                PlaylistEvent.OnResume -> {
                    if (state.currentTrackCoverId != null && !state.isLoadedCover) {
                        effects { +LoadTrackCover(state.currentTrackCoverId!!) }
                    }
                }
            }
        }
    }