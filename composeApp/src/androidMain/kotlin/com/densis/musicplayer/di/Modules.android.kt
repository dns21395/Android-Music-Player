package com.densis.musicplayer.di

import com.densis.musicplayer.data.MusicPlayer
import com.densis.musicplayer.data.PermissionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { MusicPlayer(androidApplication()) }
        single { PermissionManager(androidApplication()) }
    }