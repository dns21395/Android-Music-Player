package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.domain.entity.Track

sealed class PlaylistEvent {
    object InitScreen : PlaylistEvent()
    data class OnReceivedPlaylist(val playlist: List<Track>) : PlaylistEvent()
    data class OnTrackClicked(val track: Track) : PlaylistEvent()
    data object OpenPlayer : PlaylistEvent()
}