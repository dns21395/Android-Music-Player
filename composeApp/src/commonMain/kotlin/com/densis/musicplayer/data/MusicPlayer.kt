package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.flow.StateFlow

expect class MusicPlayer {
    suspend fun loadLibrary(): List<Track>

    fun playFrom(index: Int, tracks: List<Track>)
    fun play()
    fun pause()
    fun next()
    fun previous()
}