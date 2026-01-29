package com.densis.musicplayer.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.densis.musicplayer.data.AppLogger
import com.densis.musicplayer.player.TrackCoverLoader
import com.densis.musicplayer.playlist.presentation.store.PlaylistEffect
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent
import com.densis.musicplayer.playlist.presentation.store.PlaylistState
import com.densis.musicplayer.playlist.presentation.store.PlaylistStore
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val store: PlaylistStore,
    private val trackCoverLoader: TrackCoverLoader,
    private val appLogger: AppLogger
) : ViewModel() {

    init {
        observeEffects()
        store.start()
        onEvent(PlaylistEvent.InitScreen)
    }

    val state: StateFlow<PlaylistState> = store.states

    val effects = store.effects

    fun onEvent(event: PlaylistEvent) {
        store.accept(event)
    }

    private fun observeEffects() {
        viewModelScope.launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
            store.effects.collect { effect ->
                when (effect) {
                    is PlaylistEffect.LoadTrackCover -> {
                        appLogger.d("GTA5", "load track cover")
                        val imageBitmap = trackCoverLoader.load(effect.id)
                        store.accept(
                            PlaylistEvent.OnTrackCoverLoaded(imageBitmap)
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}