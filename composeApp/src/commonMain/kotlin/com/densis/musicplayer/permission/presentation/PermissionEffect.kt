package com.densis.musicplayer.permission.presentation

sealed class PermissionEffect {
    object RequestPermission : PermissionEffect()
}