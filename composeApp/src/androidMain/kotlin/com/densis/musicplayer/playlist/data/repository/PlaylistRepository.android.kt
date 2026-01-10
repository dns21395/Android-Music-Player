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
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC}  != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

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
            val albumIdCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (it.moveToNext()) {
                val albumId = it.getLong(albumIdCol)

                result += Track(
                    id = it.getLong(idCol).toString(),
                    title = it.getString(titleCol) ?: "",
                    artist = it.getString(artistCol) ?: "",
                    trackCoverId = albumId.toString()
                )
            }
        }

        result
    }
}