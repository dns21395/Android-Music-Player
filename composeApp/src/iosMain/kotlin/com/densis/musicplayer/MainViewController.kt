package com.densis.musicplayer

import androidx.compose.ui.window.ComposeUIViewController
import com.densis.musicplayer.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }