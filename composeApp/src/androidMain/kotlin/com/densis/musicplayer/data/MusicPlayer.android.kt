package com.densis.musicplayer.data

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.densis.musicplayer.domain.entity.Track
import com.densis.musicplayer.player.PlayerService
import com.densis.musicplayer.player.domain.entity.PlayerState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    var mediaControllerCallback: ((
        playerState: PlayerState,
        currentMusic: Track?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        iRepeatOneEnabled: Boolean,
    ) -> Unit)? =
        null

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
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artist)
                        .build()
                )
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

    actual fun getCurrentTrack(): Track? {
        return controller?.currentMediaItem?.toTrack()
    }

    // TODO release function


    private fun controllerListener() {
        controller?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                with(player) {
                    mediaControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItem?.toTrack(),
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode == Player.REPEAT_MODE_ONE
                    )
                }
            }
        })
    }

    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.PAUSED
            Player.STATE_ENDED -> PlayerState.PAUSED
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    fun MediaItem.toTrack() =
        playlist.find { it.id == mediaId }
}