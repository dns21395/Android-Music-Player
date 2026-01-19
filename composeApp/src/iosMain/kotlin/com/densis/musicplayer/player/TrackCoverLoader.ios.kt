package com.densis.musicplayer.player

import com.densis.musicplayer.data.AppLogger
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.NSNumber
import platform.Foundation.numberWithLongLong
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemPropertyPersistentID
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

actual class TrackCoverLoader(
    private val appLogger: AppLogger,
) {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun load(artworkKey: String): ByteArray? {
        appLogger.d("GTA5", "load : $artworkKey")
        val query = MPMediaQuery.songsQuery()
        query.addFilterPredicate(
            MPMediaPropertyPredicate.predicateWithValue(
                NSNumber.numberWithLongLong(artworkKey.toLong()),
                MPMediaItemPropertyPersistentID
            )
        )


        val item: MPMediaItem = (query.items?.firstOrNull() as? MPMediaItem) ?: return null

        val artwork = item.artwork ?: return null

        val uiImage = artwork.imageWithSize(
            CGSizeMake(512.0, 512.0)
        ) ?: return null

        return uiImage.toJpegByteArray()
    }
}


@OptIn(ExperimentalForeignApi::class)
fun UIImage.toJpegByteArray(quality: Double = 0.75): ByteArray? {
    val nsData: NSData = UIImageJPEGRepresentation(this, quality) ?: return null
    val length = nsData.length.toInt()
    if (length <= 0) return null

    val bytes = ByteArray(length)
    memScoped {
        memcpy(bytes.refTo(0), nsData.bytes, nsData.length)
    }
    return bytes
}


