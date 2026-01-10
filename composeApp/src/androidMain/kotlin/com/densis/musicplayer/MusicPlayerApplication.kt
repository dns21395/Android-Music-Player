package com.densis.musicplayer

import android.app.Application
import com.densis.musicplayer.di.initKoin
import org.koin.android.ext.koin.androidContext

class MusicPlayerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MusicPlayerApplication)
        }
    }
}