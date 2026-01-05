package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.data.MusicPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import money.vivid.elmslie.core.store.Actor

class PlayerActor(
    private val musicPlayer: MusicPlayer,
) : Actor<PlayerCommand, PlayerEvent>() {
    override fun execute(command: PlayerCommand): Flow<PlayerEvent> {
        return when (command) {
            PlayerCommand.GetTrack -> flow {
                val track = musicPlayer.getCurrentTrack()
                emit(PlayerEventInternal.OnReceivedCurrentTrack(track))
            }
        }
    }
}