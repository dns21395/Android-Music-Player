package com.densis.musicplayer.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.densis.musicplayer.domain.entity.Track
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent
import com.densis.musicplayer.playlist.presentation.store.PlaylistState

@Composable
fun PlaylistScreen(
    state: PlaylistState,
    onEvent: (PlaylistEvent) -> Unit,
    modifier: Modifier
) {
    if (state.playlist.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = state.playlist,
                key = { it.id }
            ) { track ->
                TrackItem(
                    track = track,
                    onEvent = { onEvent(it) }
                )
            }
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Wow, such empty")
        }
    }
}


@Composable
private fun TrackItem(
    track: Track,
    onEvent: (PlaylistEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onEvent(PlaylistEvent.OnTrackClicked(track)) })
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = track.title,
            style = MaterialTheme.typography.titleMedium
        )

        if (track.artist.isNotBlank()) {
            Text(
                text = track.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}