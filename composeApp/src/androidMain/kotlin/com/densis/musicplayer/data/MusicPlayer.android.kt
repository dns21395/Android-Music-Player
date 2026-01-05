package com.densis.musicplayer.data

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.densis.musicplayer.domain.entity.Track

actual class MusicPlayer(
    private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null
    private var playlist: List<Track> = emptyList()
    private var currentIndex: Int = -1

    actual fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
    }

    actual fun play(track: Track) {
        mediaPlayer?.release()

        val uri = Uri.withAppendedPath(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            track.id
        )

        mediaPlayer = MediaPlayer.create(context, uri).apply {
            start()
        }

        currentIndex = playlist.indexOfFirst { it.id == track.id }
    }

    actual fun pause() {
        mediaPlayer?.pause()
    }

    actual fun next() {
        if (playlist.isEmpty()) return
        val nextIndex = (currentIndex + 1).coerceAtMost(playlist.lastIndex)
        play(playlist[nextIndex])
    }

    actual fun previous() {
        if (playlist.isEmpty()) return
        val prevIndex = (currentIndex - 1).coerceAtLeast(0)
        play(playlist[prevIndex])
    }

    actual fun getCurrentTrack(): Track? {
        if (currentIndex == -1) {
            return null
        }
        return playlist[currentIndex]
    }
}