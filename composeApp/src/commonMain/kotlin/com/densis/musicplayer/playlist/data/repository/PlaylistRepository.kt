package com.densis.musicplayer.playlist.data.repository

import com.densis.musicplayer.domain.entity.Track

expect class PlaylistRepository {

    suspend fun getPlaylist(): List<Track>
}