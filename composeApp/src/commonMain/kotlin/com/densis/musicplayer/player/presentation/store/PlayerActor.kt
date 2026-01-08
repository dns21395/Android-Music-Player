package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnPlayPauseStateUpdated
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnReceivedCurrentTrack
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnReceivedTotalDuration
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnSeekPositionUpdated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import money.vivid.elmslie.core.store.Actor

class PlayerActor(
    private val musicPlayer: MusicPlayer,
) : Actor<PlayerCommand, PlayerEvent>() {
    override fun execute(command: PlayerCommand): Flow<PlayerEvent> {
        return when (command) {
            PlayerCommand.ObserveTrack -> flow {
                musicPlayer.getCurrentTrack().collect { track ->
                    emit(OnReceivedCurrentTrack(track))
                }
            }

            PlayerCommand.ObserveCurrentPosition -> flow {
                while (true) {
                    val position = withContext(Dispatchers.Main) {
                        musicPlayer.currentPosition()
                    }

                    emit(OnSeekPositionUpdated(position))
                    delay(1_000)
                }
            }

            PlayerCommand.ObservePlayPause -> flow {
                musicPlayer.observeIsPlaying().collect { isPlaying ->
                    emit(OnPlayPauseStateUpdated(isPlaying))
                }
            }


            PlayerCommand.PlayPreviousTrack -> flow {
                withContext(Dispatchers.Main) {
                    musicPlayer.previous()
                }
            }

            PlayerCommand.PlayerNextTrack -> flow {
                withContext(Dispatchers.Main) {
                    musicPlayer.next()
                }

            }

            is PlayerCommand.PlayOrPause -> flow {
                withContext(Dispatchers.Main) {
                    if (command.isPlaying) {
                        musicPlayer.pause()
                    } else {
                        musicPlayer.resume()
                    }
                }
            }

            PlayerCommand.ObserveTotalDuration -> flow {
                musicPlayer.observeTotalDuration().collect { duration ->
                    emit(OnReceivedTotalDuration(duration))
                }
            }

            is PlayerCommand.SeekTo -> flow {
                withContext(Dispatchers.Main) {
                    musicPlayer.seekTo(command.seekTo)
                }
            }
        }
    }
}