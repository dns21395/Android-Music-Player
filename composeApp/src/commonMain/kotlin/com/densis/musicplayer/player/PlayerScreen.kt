package com.densis.musicplayer.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.densis.musicplayer.player.presentation.store.PlayerEvent
import com.densis.musicplayer.player.presentation.store.PlayerState

@Composable
fun PlayerScreen(
    state: PlayerState,
    onEvent: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Cover(state.image)
        Spacer(Modifier.height(16.dp))
        Text(state.name)
        Text(state.artist)
    }
}

@Composable
fun Cover(cover: ImageBitmap?) {
    if (cover != null) {
        Image(
            bitmap = cover,
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(240.dp)
                .background(Color.DarkGray)
        )
    }
}