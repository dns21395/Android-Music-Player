package com.densis.musicplayer.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.densis.musicplayer.playlist.presentation.store.PlaylistState

@Composable
fun PlaylistScreen(
    state: PlaylistState,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Playlist screen : ${state.playlist}")
    }
}