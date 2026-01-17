package com.densis.musicplayer.data

import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemCollection
import platform.MediaPlayer.MPMediaQuery
import platform.MediaPlayer.MPMusicPlaybackState
import platform.MediaPlayer.MPMusicPlayerController
import platform.MediaPlayer.MPMusicPlayerControllerNowPlayingItemDidChangeNotification
import platform.MediaPlayer.MPMusicPlayerControllerPlaybackStateDidChangeNotification
import platform.MediaPlayer.MPMusicRepeatMode
import platform.darwin.NSObjectProtocol

actual class MusicPlayer {
    private val player = MPMusicPlayerController.systemMusicPlayer()
    private var playlist: List<Track> = emptyList()
    private var queueItems: List<MPMediaItem> = emptyList()


    private val _totalDurationMs = MutableStateFlow(0f)
    private val _isPlaying = MutableStateFlow(false)

    private var nowPlayingObserver: NSObjectProtocol? = null
    private var playbackStateObserver: NSObjectProtocol? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        player.beginGeneratingPlaybackNotifications()

        val center = NSNotificationCenter.defaultCenter

        nowPlayingObserver = center.addObserverForName(
            name = MPMusicPlayerControllerNowPlayingItemDidChangeNotification,
            `object` = player,
            queue = null
        ) { _: NSNotification? ->
            _totalDurationMs.value = currentTotalDurationMs()
            _isPlaying.value =
                player.playbackState == MPMusicPlaybackState.MPMusicPlaybackStatePlaying
        }

        playbackStateObserver = center.addObserverForName(
            name = MPMusicPlayerControllerPlaybackStateDidChangeNotification,
            `object` = player,
            queue = null
        ) { _: NSNotification? ->
            _isPlaying.value =
                player.playbackState == MPMusicPlaybackState.MPMusicPlaybackStatePlaying
        }

        _totalDurationMs.value = currentTotalDurationMs()
        _isPlaying.value = player.playbackState == MPMusicPlaybackState.MPMusicPlaybackStatePlaying
    }

    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
        queueItems = getPersistentIDs(tracks)

        val collection = MPMediaItemCollection(items = queueItems)
        player.repeatMode = MPMusicRepeatMode.MPMusicRepeatModeAll
        player.setQueueWithItemCollection(collection)
    }

    private fun getPersistentIDs(tracks: List<Track>): List<MPMediaItem> {
        val wantedIds: List<Long> = tracks.mapNotNull { it.id.toLongOrNull() }
        if (wantedIds.isEmpty()) return emptyList()

        val allItems = MPMediaQuery.songsQuery().items ?: emptyList<Any?>()

        val byId: Map<Long, MPMediaItem> =
            allItems.mapNotNull { it as? MPMediaItem }
                .associateBy { it.persistentID.toLong() }

        return wantedIds.mapNotNull { byId[it] }
    }

    actual fun play(track: Track) {
        scope.launch {
            val idx = playlist.indexOfFirst { it.id == track.id }
            val item = queueItems.getOrNull(idx) ?: return@launch

            player.nowPlayingItem = item
            player.play()
        }
    }

    actual fun playTrackId(trackId: String) {
        val trackIndex = playlist.indexOfFirst { it.id == trackId }
        val item = queueItems.getOrNull(trackIndex) ?: return

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

            _totalDurationMs.value = currentTotalDurationMs()

            trySend(track)
        }

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
        }
    }

    actual fun currentPosition(): Float {
        val sec = player.currentPlaybackTime.coerceAtLeast(0.0)
        return (sec * 1000.0).toFloat()
    }

    actual fun observeTotalDuration(): Flow<Float> = _totalDurationMs.asStateFlow()

    actual fun observeIsPlaying(): Flow<Boolean> = _isPlaying.asStateFlow()

    actual fun seekTo(position: Float) {
        val sec = (position / 1000f).toDouble()
        player.currentPlaybackTime = sec
    }

    private fun currentTotalDurationMs(): Float {
        val item = player.nowPlayingItem ?: return 0f
        val sec = item.playbackDuration.coerceAtLeast(0.0)
        return (sec * 1000.0).toFloat()
    }
}