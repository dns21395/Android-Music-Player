package com.densis.musicplayer.playlist.data.repository

import android.content.Context
import android.provider.MediaStore
import com.densis.musicplayer.domain.entity.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class PlaylistRepository(
    private val context: Context,
) {
    actual suspend fun getPlaylist(): List<Track> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Track>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC}  != 0"
        val sortOrder = "${MediaStore.Audio.AudioColumns.TITLE} ASC"

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
}