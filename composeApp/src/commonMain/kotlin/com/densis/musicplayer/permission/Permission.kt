package com.densis.musicplayer.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.densis.musicplayer.permission.presentation.PermissionEvent
import com.densis.musicplayer.permission.presentation.PermissionState
import musicplayer.composeapp.generated.resources.Res
import musicplayer.composeapp.generated.resources.permission_button
import musicplayer.composeapp.generated.resources.permission_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun Permission(
    state: PermissionState,
    onEvent: (PermissionEvent) -> Unit,
    modifier: Modifier
) {
    if (state.isScreenVisible) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(Res.string.permission_text))
            Spacer(Modifier.height(16.dp))
            Button(onClick = { onEvent(PermissionEvent.RequestPermission) }) {
                Text(stringResource(Res.string.permission_button))
            }
        }
    }
}