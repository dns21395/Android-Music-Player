package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import platform.MediaPlayer.MPMediaItemPropertyPersistentID
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery
import platform.MediaPlayer.MPMusicPlayerController

actual class MusicPlayer {
    private val player = MPMusicPlayerController.systemMusicPlayer()
    private var playlist: List<Track> = emptyList()
    private var currentIndex: Int = -1

    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
    }

    actual fun play(track: Track) {
        val predicate = MPMediaPropertyPredicate.predicateWithValue(
            value = track.id.toLong(),
            forProperty = MPMediaItemPropertyPersistentID
        )

        val query = MPMediaQuery()
        query.addFilterPredicate(predicate)

        player.setQueueWithQuery(query)
        player.play()

        currentIndex = playlist.indexOfFirst { it.id == track.id }
    }

    actual fun pause() {
        player.pause()
    }

    actual fun next() {
        player.skipToNextItem()
        currentIndex++
    }

    actual fun previous() {
        player.skipToPreviousItem()
        currentIndex--
    }
}