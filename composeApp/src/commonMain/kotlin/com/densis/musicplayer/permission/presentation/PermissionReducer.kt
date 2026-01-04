package com.densis.musicplayer.permission.presentation

import money.vivid.elmslie.core.store.StateReducer

val permissionReducer =
    object : StateReducer<PermissionEvent, PermissionState, PermissionEffect, PermissionCommand>() {
        override fun StateReducer<PermissionEvent, PermissionState, PermissionEffect, PermissionCommand>.Result.reduce(
            event: PermissionEvent
        ) {
            when (event) {
                PermissionEvent.CheckPermission -> {
                    commands { +PermissionCommand.CheckPermission }
                }

                is PermissionEvent.OnReceivedPermissionStatus -> {
                    if (event.isGranted) {
                        effects { +PermissionEffect.OpenPlaylist }
                    } else {
                        state { copy(isScreenVisible = true) }
                    }
                }

                PermissionEvent.RequestPermission -> {
                    effects { +PermissionEffect.RequestPermission }
                }
            }
        }
    }
