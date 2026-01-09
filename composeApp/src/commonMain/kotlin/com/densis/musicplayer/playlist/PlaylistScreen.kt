package com.densis.musicplayer.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.densis.musicplayer.common.presentation.Cover
import com.densis.musicplayer.domain.entity.Track
import com.densis.musicplayer.playlist.presentation.store.PlaylistEvent
import com.densis.musicplayer.playlist.presentation.store.PlaylistState
import musicplayer.composeapp.generated.resources.Res
import musicplayer.composeapp.generated.resources.pause
import musicplayer.composeapp.generated.resources.play
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlaylistScreen(
    state: PlaylistState,
    onEvent: (PlaylistEvent) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NowPlayingBar(
                state = state,
                onEvent = onEvent,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }) { innerPadding ->
        if (state.playlist.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = WindowInsets.statusBars.asPaddingValues()
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
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

@Composable
fun NowPlayingBar(
    state: PlaylistState,
    onEvent: (PlaylistEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Cover(
                cover = state.currentTrackCover,
                imageSize = 48.dp,
                emptyIconSize = 16.dp
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = state.currentTrackName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = state.currentTrackArtist,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            IconButton(onClick = { onEvent(PlaylistEvent.OnPlayPauseButtonClicked) }) {
                val painter = if (state.isPlaying) Res.drawable.pause else Res.drawable.play
                Icon(
                    painter = painterResource(painter),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}