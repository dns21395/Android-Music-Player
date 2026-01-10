package com.densis.musicplayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.densis.musicplayer.common.presentation.MusicPlayerTheme
import com.densis.musicplayer.permission.Permission
import com.densis.musicplayer.permission.PermissionViewModel
import com.densis.musicplayer.permission.presentation.PermissionEffect
import com.densis.musicplayer.permission.presentation.PermissionEvent
import com.densis.musicplayer.permission.rememberRequestPermission
import com.densis.musicplayer.player.PlayerScreen
import com.densis.musicplayer.player.PlayerViewModel
import com.densis.musicplayer.playlist.PlaylistScreen
import com.densis.musicplayer.playlist.PlaylistViewModel
import com.densis.musicplayer.playlist.presentation.store.PlaylistEffect
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.AppGraph
            ) {
                navigation<Route.AppGraph>(
                    startDestination = Route.Permission
                ) {
                    composable<Route.Permission> {
                        val viewModel = koinViewModel<PermissionViewModel>()

                        val requestPermission =
                            rememberRequestPermission { granted ->
                                viewModel.onEvent(
                                    PermissionEvent.OnReceivedPermissionStatus(
                                        granted
                                    )
                                )
                            }

                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(Unit) {
                            viewModel.onEvent(PermissionEvent.CheckPermission)
                            viewModel.effects.collect { effect ->
                                when (effect) {
                                    PermissionEffect.RequestPermission -> requestPermission()
                                    PermissionEffect.OpenPlaylist -> navController.navigate(Route.Playlist)
                                }
                            }
                        }
                        Permission(
                            state = state,
                            onEvent = { viewModel.onEvent(it) },
                            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)
                        )
                    }
                    composable<Route.Playlist> {
                        val viewModel = koinViewModel<PlaylistViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(Unit) {
                            viewModel.effects.collect { effect ->
                                when (effect) {
                                    PlaylistEffect.OpenPlayer -> navController.navigate(Route.Player)
                                    else -> Unit
                                }
                            }
                        }

                        PlaylistScreen(
                            state = state,
                            onEvent = { viewModel.onEvent(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    composable<Route.Player> {
                        val viewModel = koinViewModel<PlayerViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        PlayerScreen(
                            state = state,
                            onEvent = { viewModel.onEvent(it) },
                        )
                    }
                }
            }
        }
    }
}