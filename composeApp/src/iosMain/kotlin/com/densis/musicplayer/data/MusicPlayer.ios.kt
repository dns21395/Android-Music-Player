package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.flow.StateFlow

actual class MusicPlayer {
    actual suspend fun loadLibrary(): List<Track> {
        return emptyList()
    }

    actual fun playFrom(
        index: Int,
        tracks: List<Track>
    ) {
    }

    actual fun play() {
    }

    actual fun pause() {
    }

    actual fun next() {
    }

    actual fun previous() {
    }
}