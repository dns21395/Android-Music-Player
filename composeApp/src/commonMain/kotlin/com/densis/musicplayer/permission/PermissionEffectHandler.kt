package com.densis.musicplayer.permission

import androidx.compose.runtime.Composable

@Composable
expect fun rememberRequestPermission(
    onResult: (Boolean) -> Unit
): () -> Unit