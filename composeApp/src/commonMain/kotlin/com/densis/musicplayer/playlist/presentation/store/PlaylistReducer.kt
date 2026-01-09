package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.GetPlaylist
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.ObserveCurrentTrack
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.PlayOrPause
import com.densis.musicplayer.playlist.presentation.store.PlaylistCommand.PlayTrack
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

                PlaylistEvent.OpenPlayer -> {
                    effects { +OpenPlayer }
                }

                is PlaylistEvent.OnReceivedCurrentTrack -> {
                    val track = event.track
                    state {
                        copy(
                            currentTrackArtist = track?.artist ?: "",
                            currentTrackName = track?.title ?: "",
                            currentTrackCover = null,
                        )
                    }
                    if (track?.trackCoverId != null) {
                        effects { +LoadTrackCover(track.trackCoverId) }
                    }
                }

                is PlaylistEvent.OnTrackCoverLoaded -> {
                    state {
                        copy(currentTrackCover = event.trackCover)
                    }
                }

                PlaylistEvent.OnPlayPauseButtonClicked -> {
                    commands { +PlayOrPause(state.isPlaying) }
                }

                is PlaylistEvent.OnPlayPauseStateUpdated -> {
                    state { copy(isPlaying = event.isPlaying) }
                }
            }
        }
    }