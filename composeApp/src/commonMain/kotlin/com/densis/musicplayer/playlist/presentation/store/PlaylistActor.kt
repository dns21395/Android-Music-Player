package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.playlist.data.repository.PlaylistRepository
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import money.vivid.elmslie.core.store.Actor

class PlaylistActor(
    private val playlistRepository: PlaylistRepository,
    private val musicPlayer: MusicPlayer,
) : Actor<PlaylistCommand, PlaylistEvent>() {
    override fun execute(command: PlaylistCommand): Flow<PlaylistEvent> {
        return when (command) {
            PlaylistCommand.GetPlaylist -> flow {
                val playlist = playlistRepository.getPlaylist()
                musicPlayer.setPlaylist(playlist)
                emit(OnReceivedPlaylist(playlist))
            }

            is PlaylistCommand.PlayTrack -> {
                musicPlayer.play(command.track)
                emptyFlow()
            }
        }
    }
}