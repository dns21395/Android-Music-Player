package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.domain.entity.Track

sealed class PlaylistEvent {
    object InitScreen : PlaylistEvent()
    data object OnPlayPauseButtonClicked : PlaylistEvent()
    data class OnReceivedPlaylist(val playlist: List<Track>) : PlaylistEvent()
    data class OnTrackClicked(val track: Track) : PlaylistEvent()
    data class OpenPlayer(val trackId: String? = null) : PlaylistEvent()
    data class OnReceivedCurrentTrack(val track: Track?) : PlaylistEvent()
    data class OnTrackCoverLoaded(val trackCover: ByteArray?) : PlaylistEvent()
    data class OnPlayPauseStateUpdated(val isPlaying: Boolean) : PlaylistEvent()
}