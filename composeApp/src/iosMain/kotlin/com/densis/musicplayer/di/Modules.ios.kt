package com.densis.musicplayer.di

import com.densis.musicplayer.data.AppLogger
import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.data.PermissionManager
import com.densis.musicplayer.player.TrackCoverLoader
import com.densis.musicplayer.playlist.data.repository.PlaylistRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { MusicPlayer() }
        single { PermissionManager() }
        factory { AppLogger() }
        factory { PlaylistRepository() }
        factory { TrackCoverLoader(get()) }
    }