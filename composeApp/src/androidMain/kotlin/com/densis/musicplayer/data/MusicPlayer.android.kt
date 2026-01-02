package com.densis.musicplayer.data

import android.content.Context
import android.provider.MediaStore
import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

actual class MusicPlayer(
    private val context: Context
) {
    actual suspend fun loadLibrary(): List<Track> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Track>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC}  != 0"
        val sortOrder = "${MediaStore.Audio.AudioColumns.TITLE} COLLATE LOCALIZED ASC"

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        cursor?.use {
            val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (it.moveToNext()) {
                result += Track(
                    id = it.getLong(idCol).toString(),
                    title = it.getString(titleCol) ?: "",
                    artist = it.getString(artistCol) ?: ""
                )
            }
        }

        result
    }

    actual fun playFrom(
        index: Int,
        tracks: List<Track>
    ) {
    }

    actual fun play() {
    }

    actual fun pause() {
    }

    actual fun next() {
    }

    actual fun previous() {
    }
}