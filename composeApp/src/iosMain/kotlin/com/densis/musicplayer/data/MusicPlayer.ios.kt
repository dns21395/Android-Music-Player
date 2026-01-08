package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSNumber
import platform.Foundation.numberWithLongLong
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemCollection
import platform.MediaPlayer.MPMediaItemPropertyPersistentID
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery
import platform.MediaPlayer.MPMusicPlayerController
import platform.MediaPlayer.MPMusicPlayerControllerNowPlayingItemDidChangeNotification
import platform.MediaPlayer.MPMusicRepeatMode
import platform.darwin.NSObjectProtocol

actual class MusicPlayer {
    private val player = MPMusicPlayerController.systemMusicPlayer()
    private var playlist: List<Track> = emptyList()
    private var queueItems: List<MPMediaItem> = emptyList()

    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
        queueItems = getPersistentIDs(tracks)

        val collection = MPMediaItemCollection(items = queueItems)
        player.repeatMode = MPMusicRepeatMode.MPMusicRepeatModeAll
        player.setQueueWithItemCollection(collection)
    }

    private fun getPersistentIDs(tracks: List<Track>): List<MPMediaItem> {
        val ids: List<NSNumber> = tracks.mapNotNull { it.id.toLongOrNull() }
            .map { NSNumber.numberWithLongLong(it) }

        if (ids.isEmpty()) {
            queueItems = emptyList()
            return emptyList()
        }

        val predicate = MPMediaPropertyPredicate.predicateWithValue(
            value = ids,
            forProperty = MPMediaItemPropertyPersistentID
        )

        val query = MPMediaQuery()
        query.addFilterPredicate(predicate)

        val itemsAny = query.items ?: emptyList<Any?>()
        return itemsAny.mapNotNull { it as? MPMediaItem }
    }

    actual fun play(track: Track) {
        val idx = playlist.indexOfFirst { it.id == track.id }
        val item = queueItems.getOrNull(idx) ?: return

        player.nowPlayingItem = item
        player.play()
    }

    actual fun resume() = player.play()
    actual fun pause() = player.pause()
    actual fun next() = player.skipToNextItem()
    actual fun previous() = player.skipToPreviousItem()

    actual fun getCurrentTrack(): Flow<Track?> = callbackFlow {
        fun emitNowPlaying() {
            val item = player.nowPlayingItem
            val id = item?.persistentID?.toString()
            val track = id?.let { pid -> playlist.firstOrNull { it.id == pid } }
            trySend(track)
        }

        player.beginGeneratingPlaybackNotifications()

        val center = NSNotificationCenter.defaultCenter

        val obs1: NSObjectProtocol = center.addObserverForName(
            name = MPMusicPlayerControllerNowPlayingItemDidChangeNotification,
            `object` = player,
            queue = null
        ) { _: NSNotification? ->
            emitNowPlaying()
        }

        emitNowPlaying()

        awaitClose {
            center.removeObserver(obs1)
            player.endGeneratingPlaybackNotifications()
        }
    }
}