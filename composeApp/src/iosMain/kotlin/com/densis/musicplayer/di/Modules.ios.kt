package com.densis.musicplayer.di

import com.densis.musicplayer.data.MusicPlayer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { MusicPlayer() }
    }