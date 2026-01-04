package com.densis.musicplayer.di

import com.densis.musicplayer.permission.PermissionViewModel
import com.densis.musicplayer.permission.presentation.PermissionActor
import com.densis.musicplayer.permission.presentation.PermissionState
import com.densis.musicplayer.permission.presentation.PermissionStore
import com.densis.musicplayer.permission.presentation.permissionReducer
import com.densis.musicplayer.playlist.PlaylistViewModel
import com.densis.musicplayer.playlist.presentation.store.PlaylistActor
import com.densis.musicplayer.playlist.presentation.store.PlaylistReducer
import com.densis.musicplayer.playlist.presentation.store.PlaylistState
import com.densis.musicplayer.playlist.presentation.store.PlaylistStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    factory(named("permission")) {
        PermissionStore(
            initialState = PermissionState(),
            reducer = permissionReducer,
            actor = PermissionActor(get())
        )
    }

    factory(named("playlist")) {
        PlaylistStore(
            initialState = PlaylistState(),
            reducer = PlaylistReducer,
            actor = PlaylistActor(get(), get())
        )
    }

    viewModel { PermissionViewModel(get(named("permission"))) }
    viewModel { PlaylistViewModel(get(named("playlist"))) }
}