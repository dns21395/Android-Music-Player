package com.densis.musicplayer.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.densis.musicplayer.common.presentation.Cover
import com.densis.musicplayer.player.presentation.store.PlayerEvent
import com.densis.musicplayer.player.presentation.store.PlayerEventUi
import com.densis.musicplayer.player.presentation.store.PlayerState
import com.densis.musicplayer.player.presentation.store.toTime
import musicplayer.composeapp.generated.resources.Res
import musicplayer.composeapp.generated.resources.ic_arrow_back
import musicplayer.composeapp.generated.resources.pause
import musicplayer.composeapp.generated.resources.play
import musicplayer.composeapp.generated.resources.skip_next
import musicplayer.composeapp.generated.resources.skip_previous
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerScreen(
    state: PlayerState,
    coverBytes: ByteArray?,
    onEvent: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { onEvent(PlayerEventUi.OnBackButtonClicked) },
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = null,
                tint = Color.White,
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Cover(
                coverBytes = coverBytes,
                imageSize = 240.dp,
                emptyIconSize = 120.dp,
                emptyBackgroundColor = Color(0xFF2A2A2A)
            )

            Spacer(Modifier.height(24.dp))
            Slider(
                value = state.currentTime,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                valueRange = 0f..state.totalTime,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onBackground,
                    activeTrackColor = MaterialTheme.colorScheme.onBackground,
                    inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.25f
                    )
                ),
                onValueChange = {
                    onEvent(PlayerEventUi.StartDragging(it))
                },
                onValueChangeFinished = {
                    onEvent(PlayerEventUi.StopDragging)
                }
            )
            val totalText by remember(state.totalTime) {
                derivedStateOf { state.totalTime.toLong().toTime() }
            }

            val currentText by remember(state.currentTime) {
                derivedStateOf { state.currentTime.toLong().toTime() }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(currentText, color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Text(totalText, color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
            Text(
                state.name, style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.6f),
                        offset = Offset(0f, 2f),
                        blurRadius = 8f,
                    )
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                state.artist, style = MaterialTheme.typography.titleSmall.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.6f),
                        offset = Offset(0f, 2f),
                        blurRadius = 8f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))
            Row {
                IconButton(onClick = { onEvent(PlayerEventUi.OnPreviousButtonClicked) }) {
                    Icon(
                        painter = painterResource(Res.drawable.skip_previous),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { onEvent(PlayerEventUi.OnPlayPauseButtonClicked) }) {
                    val painter = if (state.isPlaying) Res.drawable.pause else Res.drawable.play
                    Icon(
                        painter = painterResource(painter),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { onEvent(PlayerEventUi.OnNextButtonClicked) }) {
                    Icon(
                        painter = painterResource(Res.drawable.skip_next),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)

                    )
                }
            }
        }
    }
}
