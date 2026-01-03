package com.densis.musicplayer.permission

import androidx.lifecycle.ViewModel
import com.densis.musicplayer.permission.presentation.PermissionState
import com.densis.musicplayer.permission.presentation.PermissionStore
import kotlinx.coroutines.flow.StateFlow

class PermissionViewModel(private val store: PermissionStore) : ViewModel() {

    val state: StateFlow<PermissionState> = store.states
}