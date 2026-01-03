package com.densis.musicplayer.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.MediaPlayer.MPMediaLibrary
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusAuthorized
import kotlin.coroutines.resume


@Composable
actual fun rememberRequestPermission(
    onResult: (Boolean) -> Unit
): () -> Unit {
    val scope = rememberCoroutineScope()

    return {
        scope.launch {
            val granted = suspendCancellableCoroutine<Boolean> { cont ->
                MPMediaLibrary.requestAuthorization { status ->
                    cont.resume(
                        status == MPMediaLibraryAuthorizationStatusAuthorized
                    )
                }
            }
            onResult(granted)
        }
    }
}