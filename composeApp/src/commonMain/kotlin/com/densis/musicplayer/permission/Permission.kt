package com.densis.musicplayer.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.densis.musicplayer.permission.presentation.PermissionEvent
import com.densis.musicplayer.permission.presentation.PermissionState

@Composable
fun Permission(
    state: PermissionState,
    onEvent: (PermissionEvent) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text("you tried : ${state.tryCount} times")
        Text("is granted : ${state.isPermissionGranted}")
        Button(onClick = { onEvent(PermissionEvent.CheckPermission) }) {
            Text("check permission")
        }
    }
}