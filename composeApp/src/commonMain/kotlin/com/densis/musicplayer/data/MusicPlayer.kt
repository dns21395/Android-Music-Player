package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.flow.Flow

expect class MusicPlayer {
    fun setPlaylist(tracks: List<Track>)
    fun getCurrentTrack(): Flow<Track?>
    fun play(track: Track)
    fun resume()
    fun pause()
    fun next()
    fun previous()
}