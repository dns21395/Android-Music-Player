package com.densis.musicplayer.di

import com.densis.musicplayer.permission.PermissionViewModel
import com.densis.musicplayer.permission.presentation.PermissionActor
import com.densis.musicplayer.permission.presentation.PermissionState
import com.densis.musicplayer.permission.presentation.PermissionStore
import com.densis.musicplayer.permission.presentation.permissionReducer
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

    viewModel { PermissionViewModel(get(named("permission")), get()) }
}