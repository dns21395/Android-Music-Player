package com.densis.musicplayer.permission

import androidx.lifecycle.ViewModel
import com.densis.musicplayer.data.AppLogger
import com.densis.musicplayer.permission.presentation.PermissionEvent
import com.densis.musicplayer.permission.presentation.PermissionState
import com.densis.musicplayer.permission.presentation.PermissionStore
import kotlinx.coroutines.flow.StateFlow

class PermissionViewModel(
    private val store: PermissionStore,
    private val appLogger: AppLogger
) : ViewModel() {
    val state: StateFlow<PermissionState> = store.states
    val effects = store.effects


    fun onEvent(event: PermissionEvent) {
        store.accept(event)
    }

    fun permissionLog(isGranted: Boolean) {
        appLogger.d("GTA5", "is granted : $isGranted")
    }
}