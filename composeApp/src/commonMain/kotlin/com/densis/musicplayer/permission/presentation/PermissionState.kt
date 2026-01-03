package com.densis.musicplayer.permission.presentation

data class PermissionState(
    val tryCount: Int = 0,
    val isPermissionGranted: Boolean = false
)