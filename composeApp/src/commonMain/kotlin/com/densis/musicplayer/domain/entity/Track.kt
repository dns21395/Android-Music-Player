package com.densis.musicplayer.domain.entity

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val trackCoverId: String? = null // Android: albumId, iOS: persistentID
)