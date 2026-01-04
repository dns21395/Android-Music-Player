package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.domain.entity.Track

data class PlaylistState(
    val playlist: List<Track> = emptyList(),
)