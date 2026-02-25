package com.densis.musicplayer.playlist.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.playlist.data.repository.PlaylistRepository
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent.OnReceivedPlaylist
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent.OpenPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import money.vivid.elmslie.core.store.Actor

class PlaylistActor(
    private val playlistRepository: PlaylistRepository,
    private val musicPlayer: MusicPlayer,
) : Actor<PlaylistCommand, PlaylistEvent>() {
    override fun execute(command: PlaylistCommand): Flow<PlaylistEvent> {
        return when (command) {
            PlaylistCommand.GetPlaylist -> flow {
                val playlist = playlistRepository.getPlaylist()
                withContext(Dispatchers.Main) {
                    musicPlayer.setPlaylist(playlist)
                }

                emit(OnReceivedPlaylist(playlist))
            }

            is PlaylistCommand.PlayTrack -> flow {
                emit(OpenPlayer(command.track.id))
            }

            PlaylistCommand.ObserveCurrentTrack -> flow {
                musicPlayer.getCurrentTrack().collect { track ->
                    emit(PlaylistEvent.OnReceivedCurrentTrack(track))
                }
            }

            is PlaylistCommand.PlayOrPause -> flow {
                withContext(Dispatchers.Main) {
                    if (command.isPlaying) {
                        musicPlayer.pause()
                    } else {
                        musicPlayer.resume()
                    }
                }
            }

            PlaylistCommand.ObservePlayPause -> flow {
                musicPlayer.observeIsPlaying().collect { isPlaying ->
                    emit(PlaylistEvent.OnPlayPauseStateUpdated(isPlaying))
                }
            }
        }
    }
}