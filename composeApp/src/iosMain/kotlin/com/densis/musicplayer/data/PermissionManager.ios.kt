package com.densis.musicplayer.data

import platform.MediaPlayer.MPMediaLibrary
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusAuthorized

actual class PermissionManager {
    actual fun isGranted(): Boolean {
        return MPMediaLibrary.authorizationStatus() ==
                MPMediaLibraryAuthorizationStatusAuthorized
    }
}