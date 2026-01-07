package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnReceivedCurrentTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import money.vivid.elmslie.core.store.Actor

class PlayerActor(
    private val musicPlayer: MusicPlayer,
) : Actor<PlayerCommand, PlayerEvent>() {
    override fun execute(command: PlayerCommand): Flow<PlayerEvent> {
        return when (command) {
            PlayerCommand.GetTrack -> flow {
                val track = withContext(Dispatchers.Main) {
                    musicPlayer.getCurrentTrack()
                }
                emit(OnReceivedCurrentTrack(track))
            }

            PlayerCommand.PlayPreviousTrack -> flow {
                val track = withContext(Dispatchers.Main) {
                    musicPlayer.previous()
                    musicPlayer.getCurrentTrack()
                }
                emit(OnReceivedCurrentTrack(track))
            }

            PlayerCommand.PlayerNextTrack -> flow {
                val track = withContext(Dispatchers.Main) {
                    musicPlayer.next()
                    musicPlayer.getCurrentTrack()
                }

                emit(OnReceivedCurrentTrack(track))
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
        }
    }
}