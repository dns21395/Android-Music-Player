package com.densis.musicplayer.di

import com.densis.musicplayer.data.AppLogger
import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.data.PermissionManager
import com.densis.musicplayer.player.TrackCoverLoader
import com.densis.musicplayer.playlist.data.repository.PlaylistRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { MusicPlayer(androidApplication()) }
        single { PermissionManager(androidApplication()) }
        factory { AppLogger() }
        factory { PlaylistRepository(androidApplication()) }
        factory { TrackCoverLoader(androidApplication()) }
    }