package com.densis.musicplayer.data

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.densis.musicplayer.domain.entity.Track
import com.densis.musicplayer.player.PlayerService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class MusicPlayer(
    private val context: Context,
    private val appLogger: AppLogger,
) {
    private var playlist: List<Track> = emptyList()
    private var mediaItems: List<MediaItem> = emptyList()

    private val controllerFuture: ListenableFuture<MediaController>
    private var controller: MediaController? = null
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    private var currentTrack: MutableStateFlow<Track?> = MutableStateFlow(null)
    private var totalDuration: MutableStateFlow<Float> = MutableStateFlow(0f)
    private var isPlayingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        val token = SessionToken(context, ComponentName(context, PlayerService::class.java))
        controllerFuture = MediaController.Builder(context, token).buildAsync()

        controllerFuture.addListener(
            {
                controller = controllerFuture.get()
                controllerListener()
            },
            MoreExecutors.directExecutor()
        )
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks

        mediaItems = tracks.map { track ->
            val uri: Uri = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                track.id
            )

            MediaItem.Builder()
                .setMediaId(track.id)
                .setUri(uri)
                .build()
        }


        scope.launch {
            val controller = controllerFuture.await()
            controller.setMediaItems(mediaItems)
            controller.prepare()
        }
    }

    suspend fun <T> ListenableFuture<T>.await(): T =
        suspendCancellableCoroutine { cont ->
            addListener({
                try {
                    cont.resume(get())
                } catch (e: CancellationException) {
                    cont.cancel(e)
                } catch (t: Throwable) {
                    cont.resumeWithException(t)
                }
            }, MoreExecutors.directExecutor())

            cont.invokeOnCancellation {
                cancel(true)
            }
        }

    actual fun play(track: Track) {
        controller?.apply {
            seekToDefaultPosition(playlist.indexOf(track))
            playWhenReady = true
            prepare()
        }
    }

    actual fun resume() {
        controller?.play()
    }

    actual fun pause() {
        controller?.pause()
    }

    actual fun next() {
        controller?.seekToNext()
    }

    actual fun previous() {
        controller?.seekToPrevious()
    }

    actual fun getCurrentTrack(): Flow<Track?> {
        return currentTrack
    }

    // TODO release function

    private fun controllerListener() {
        controller?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                with(player) {
                    scope.launch {
                        currentTrack.emit(currentMediaItem?.toTrack())
                        totalDuration.emit(duration.coerceAtLeast(0).toFloat())
                        isPlayingState.emit(isPlaying)
                    }
                }
            }
        })
    }

    fun MediaItem.toTrack() =
        playlist.find { it.id == mediaId }

    actual fun currentPosition(): Float {
        return controller?.currentPosition?.toFloat() ?: 0f
    }

    actual fun observeTotalDuration(): Flow<Float> {
        return totalDuration
    }

    actual fun observeIsPlaying(): Flow<Boolean> {
        return isPlayingState
    }

    actual fun seekTo(position: Float) {
        controller?.seekTo(position.toLong())
    }
}