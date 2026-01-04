package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track

expect class MusicPlayer {
    fun setPlaylist(tracks: List<Track>)
    fun play(track: Track)
    fun pause()
    fun next()
    fun previous()
}