package com.densis.musicplayer.data

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.densis.musicplayer.domain.entity.Track

actual class MusicPlayer(
    private val context: Context,
    private val player: ExoPlayer
) {
    private var playlist: List<Track> = emptyList()

    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks

        val items = tracks.map { track ->
            val uri = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                track.id
            )
            MediaItem.Builder()
                .setUri(uri)
                .setMediaId(track.id)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artist)
                        .build()
                )
                .build()
        }

        player.setMediaItems(items)
        player.prepare()
        player.repeatMode = Player.REPEAT_MODE_ALL
    }

    actual fun play(track: Track) {
        val index = playlist.indexOfFirst { it.id == track.id }
        if (index == -1) return

        player.seekTo(index, 0L)
        player.play()
    }

    actual fun resume() {
        player.play()
    }

    actual fun pause() {
        player.pause()
    }

    actual fun next() {
        player.seekToNextMediaItem()
        player.play()
    }

    actual fun previous() {
        player.seekToPreviousMediaItem()
        player.play()
    }

    actual fun getCurrentTrack(): Track? {
        val idx = player.currentMediaItemIndex
        return playlist.getOrNull(idx)
    }
}