package com.densis.musicplayer.player

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class TrackCoverLoader(
    private val context: Context,
) {
    actual suspend fun load(artworkKey: String): ImageBitmap? =
        withContext(Dispatchers.IO) {
            val albumId = artworkKey.toLongOrNull() ?: return@withContext null

            val uri = "content://media/external/audio/albumart/$albumId".toUri()

            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()
                }
            } catch (e: Exception) {
                null
            }
        }
}