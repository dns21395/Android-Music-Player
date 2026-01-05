package com.densis.musicplayer.playlist.data.repository

import com.densis.musicplayer.domain.entity.Track
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemPropertyArtist
import platform.MediaPlayer.MPMediaItemPropertyTitle
import platform.MediaPlayer.MPMediaQuery

actual class PlaylistRepository {
    actual suspend fun getPlaylist(): List<Track> {
        val query = MPMediaQuery.songsQuery()
        val items = query.items ?: return emptyList()

        val result = ArrayList<Track>(items.size)

        for (obj in items) {
            val item = obj as MPMediaItem

            val title = (item.valueForProperty(MPMediaItemPropertyTitle) as? String).orEmpty()
            val artist = (item.valueForProperty(MPMediaItemPropertyArtist) as? String).orEmpty()
            val id = item.persistentID.toString()

            result += Track(id = id, title = title, artist = artist, trackCoverId = id)
        }

        return result.sortedBy { it.title.lowercase() }
    }
}