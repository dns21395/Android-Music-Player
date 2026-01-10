package com.densis.musicplayer.playlist.presentation.store

import money.vivid.elmslie.core.store.ElmStore

typealias PlaylistStore = ElmStore<
        PlaylistEvent,
        PlaylistState,
        PlaylistEffect,
        PlaylistCommand>
