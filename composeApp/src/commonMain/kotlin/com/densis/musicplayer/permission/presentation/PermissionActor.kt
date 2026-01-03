package com.densis.musicplayer.permission.presentation

import com.densis.musicplayer.data.PermissionManager
import com.densis.musicplayer.permission.presentation.PermissionEvent.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import money.vivid.elmslie.core.store.Actor

class PermissionActor(
    private val permissionManager: PermissionManager
) : Actor<PermissionCommand, PermissionEvent>() {

    override fun execute(command: PermissionCommand): Flow<PermissionEvent> {
        return when (command) {
            PermissionCommand.CheckPermission -> flow {
                val isGranted = permissionManager.isGranted()
                emit(OnReceivedPermissionStatus(isGranted))
            }
        }
    }
}