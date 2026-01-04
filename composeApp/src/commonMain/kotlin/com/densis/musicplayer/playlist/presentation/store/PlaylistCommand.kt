package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.domain.entity.Track

sealed class PlaylistCommand {
    object GetPlaylist : PlaylistCommand()
    data class PlayTrack(val track: Track) : PlaylistCommand()
}