package com.densis.musicplayer.permission.presentation

sealed class PermissionCommand {
    object CheckPermission : PermissionCommand()
}