package com.densis.musicplayer.playlist

import androidx.lifecycle.ViewModel
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent
import com.densis.musicplayer.playlist.presentation.store.PlaylistState
import com.densis.musicplayer.playlist.presentation.store.PlaylistStore
import kotlinx.coroutines.flow.StateFlow

class PlaylistViewModel(
    private val store: PlaylistStore,
) : ViewModel() {

    init {
        onEvent(PlaylistEvent.InitScreen)
    }

    val state: StateFlow<PlaylistState> = store.states

    fun onEvent(event: PlaylistEvent) {
        store.accept(event)
    }
}