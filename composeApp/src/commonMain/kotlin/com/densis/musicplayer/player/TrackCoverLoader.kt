package com.densis.musicplayer.player

expect class TrackCoverLoader {
    suspend fun load(artworkKey: String): ByteArray?
}