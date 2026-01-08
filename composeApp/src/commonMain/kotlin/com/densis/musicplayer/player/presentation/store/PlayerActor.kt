package com.densis.musicplayer.player.presentation.store

import com.densis.musicplayer.data.AppLogger
import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnPlayPauseStateUpdated
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnReceivedCurrentTrack
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnReceivedTotalDuration
import com.densis.musicplayer.player.presentation.store.PlayerEventInternal.OnSeekPositionUpdated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import money.vivid.elmslie.core.store.Actor

class PlayerActor(
    private val musicPlayer: MusicPlayer,
   val  appLogger: AppLogger,
) : Actor<PlayerCommand, PlayerEvent>() {

    private var positionJob: Job? = null


    override fun execute(command: PlayerCommand): Flow<PlayerEvent> {
        return when (command) {
            PlayerCommand.ObserveTrack -> flow {
                musicPlayer.getCurrentTrack().collect { track ->
                    emit(OnReceivedCurrentTrack(track))
                }
            }

            PlayerCommand.ObserveCurrentPosition -> channelFlow {
                if (positionJob == null) {
                    positionJob = launch {
                        while (isActive) {
                            val position = withContext(Dispatchers.Main) { musicPlayer.currentPosition() }
                            send(OnSeekPositionUpdated(position))
                            delay(1_000)
                        }
                    }
                }

                awaitClose {
                    positionJob?.cancel()
                    positionJob = null
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
                emit(PlayerEventInternal.OnSeekToUpdated)
            }

            is PlayerCommand.StopObserveCurrentPosition -> {
                positionJob?.cancel()
                positionJob = null
                emptyFlow()
            }
        }
    }
}