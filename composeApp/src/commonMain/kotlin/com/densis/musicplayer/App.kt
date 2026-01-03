package com.densis.musicplayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.densis.musicplayer.permission.Permission
import com.densis.musicplayer.permission.PermissionViewModel
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

                    val permissionState by viewModel.state.collectAsStateWithLifecycle()

                    Permission(
                        state = permissionState,
                        onEvent = { viewModel.onEvent(it) },
                        modifier = Modifier.fillMaxSize().statusBarsPadding())
                }
            }
        }
    }
}