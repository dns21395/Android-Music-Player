package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.playlist.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import money.vivid.elmslie.core.store.Actor

class PlaylistActor(
    private val playlistRepository: PlaylistRepository
) : Actor<PlaylistCommand, PlaylistEvent>() {
    override fun execute(command: PlaylistCommand): Flow<PlaylistEvent> {
        return when (command) {
            PlaylistCommand.GetPlaylist -> flow {
                emit(PlaylistEvent.OnReceivedPlaylist(playlistRepository.getPlaylist()))
            }
        }
    }
}