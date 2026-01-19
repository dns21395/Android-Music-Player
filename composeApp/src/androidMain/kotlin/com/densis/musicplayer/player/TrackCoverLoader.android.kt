package com.densis.musicplayer.player

import android.content.Context
import androidx.core.net.toUri
import com.densis.musicplayer.data.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class TrackCoverLoader(
    private val context: Context,
    private val appLogger: AppLogger,
) {
    actual suspend fun load(artworkKey: String): ByteArray? =
        withContext(Dispatchers.IO) {
            appLogger.d("GTA5", "load : $artworkKey")
            val albumId = artworkKey.toLongOrNull() ?: return@withContext null
            val uri = "content://media/external/audio/albumart/$albumId".toUri()

            try {
                context.contentResolver
                    .openInputStream(uri)
                    ?.use { it.readBytes() }
            } catch (e: Exception) {
                null
            }
        }
}