package com.densis.musicplayer.player

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayerService : MediaSessionService(), KoinComponent {

    private val player: ExoPlayer by inject()
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()

        mediaSession = MediaSession.Builder(this, player)
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}