package com.densis.musicplayer.playlist.presentation.store

sealed class PlaylistCommand {
    object GetPlaylist : PlaylistCommand()
}