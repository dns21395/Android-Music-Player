package com.densis.musicplayer.permission.presentation

import money.vivid.elmslie.core.store.ElmStore

typealias PermissionStore = ElmStore<
        PermissionEvent,
        PermissionState,
        PermissionEffect,
        PermissionCommand>