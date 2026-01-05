package com.densis.musicplayer.player.presentation.store

import money.vivid.elmslie.core.store.ElmStore

typealias PlayerStore = ElmStore<
        PlayerEvent,
        PlayerState,
        PlayerEffect,
        PlayerCommand>