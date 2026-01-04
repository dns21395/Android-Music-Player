package com.densis.musicplayer.playlist.presentation.store

import money.vivid.elmslie.core.store.StateReducer

val PlaylistReducer =
    object : StateReducer<PlaylistEvent, PlaylistState, PlaylistEffect, PlaylistCommand>() {
        override fun StateReducer<PlaylistEvent, PlaylistState, PlaylistEffect, PlaylistCommand>.Result.reduce(
            event: PlaylistEvent
        ) {
            when (event) {
                PlaylistEvent.InitScreen -> {
                    commands { +PlaylistCommand.GetPlaylist }
                }

                is PlaylistEvent.OnReceivedPlaylist -> {
                    state { copy(playlist = event.playlist) }
                }

                is PlaylistEvent.OnTrackClicked -> {
                    commands { +PlaylistCommand.PlayTrack(event.track) }
                }
            }
        }
    }