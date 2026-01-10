package com.densis.musicplayer.permission.presentation

sealed class PermissionEvent {
    object CheckPermission : PermissionEvent()
    data class OnReceivedPermissionStatus(val isGranted: Boolean) : PermissionEvent()
    object RequestPermission : PermissionEvent()
}