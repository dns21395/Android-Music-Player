package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import money.vivid.elmslie.core.store.Actor

class PlayerActor(
    private val musicPlayer: MusicPlayer,
) : Actor<PlayerCommand, PlayerEvent>() {
    override fun execute(command: PlayerCommand): Flow<PlayerEvent> {
        return when (command) {
            PlayerCommand.GetTrack -> flow {
                val track = musicPlayer.getCurrentTrack()
                emit(OnReceivedCurrentTrack(track))
            }

            PlayerCommand.PlayPreviousTrack -> flow {
                musicPlayer.previous()
                val track = musicPlayer.getCurrentTrack()
                emit(OnReceivedCurrentTrack(track))
            }
            PlayerCommand.PlayerNextTrack -> flow {
                musicPlayer.next()
                val track = musicPlayer.getCurrentTrack()
                emit(OnReceivedCurrentTrack(track))
            }

            is PlayerCommand.PlayOrPause -> {
                if (command.isPlaying) {
                    musicPlayer.pause()
                } else {
                    musicPlayer.resume()
                }
                emptyFlow()
            }
        }
    }
}