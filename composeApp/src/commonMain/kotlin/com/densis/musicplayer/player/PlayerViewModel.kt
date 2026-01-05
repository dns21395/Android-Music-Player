package com.densis.musicplayer.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.densis.musicplayer.player.presentation.store.PlayerEffect
import com.densis.musicplayer.player.presentation.store.PlayerEvent
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal
import com.densis.musicplayer.player.presentation.store.PlayerEventUi
import com.densis.musicplayer.player.presentation.store.PlayerState
import com.densis.musicplayer.player.presentation.store.PlayerStore
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val store: PlayerStore,
    private val trackCoverLoader: TrackCoverLoader,
) : ViewModel() {
    init {
        store.start()
        observeEffects()
        onEvent(PlayerEventUi.InitScreen)
    }

    val state: StateFlow<PlayerState> = store.states

    val effects = store.effects

    fun onEvent(event: PlayerEvent) {
        store.accept(event)
    }

    private fun observeEffects() {
        viewModelScope.launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
            store.effects.collect { effect ->
                when (effect) {
                    is PlayerEffect.LoadTrackCover -> {
                        val imageBitmap = trackCoverLoader.load(effect.id)
                        store.accept(
                            PlayerEventInternal.OnTrackCoverLoaded(imageBitmap)
                        )
                    }
                }
            }
        }
    }
}
