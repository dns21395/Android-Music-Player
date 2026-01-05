package com.densis.musicplayer.player

import androidx.lifecycle.ViewModel
import com.densis.musicplayer.player.presentation.store.PlayerEvent
import com.densis.musicplayer.player.presentation.store.PlayerEventUi
import com.densis.musicplayer.player.presentation.store.PlayerState
import com.densis.musicplayer.player.presentation.store.PlayerStore
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel(
    private val store: PlayerStore
) : ViewModel() {
    init {
        store.start()
        onEvent(PlayerEventUi.InitScreen)
    }

    val state: StateFlow<PlayerState> = store.states

    fun onEvent(event: PlayerEvent) {
        store.accept(event)
    }
}