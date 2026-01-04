package com.densis.musicplayer

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AppGraph : Route

    @Serializable
    data object Permission : Route

    @Serializable
    data object Playlist : Route
}