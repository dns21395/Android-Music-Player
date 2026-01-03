package com.densis.musicplayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.densis.musicplayer.permission.Permission
import com.densis.musicplayer.permission.PermissionViewModel
import com.densis.musicplayer.permission.presentation.PermissionEffect
import com.densis.musicplayer.permission.rememberRequestPermission
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
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
                        rememberRequestPermission { granted -> viewModel.permissionLog(granted) }

                    LaunchedEffect(Unit) {
                        viewModel.effects.collect { effect ->
                            when (effect) {
                                PermissionEffect.RequestPermission -> {
                                    requestPermission()
                                }
                            }
                        }
                    }

                    Permission(
                        onEvent = { viewModel.onEvent(it) },
                        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)
                    )
                }
            }
        }
    }
}