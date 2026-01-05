package com.densis.musicplayer.player

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.densis.musicplayer.playlist.data.repository.toSkiaImage
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSNumber
import platform.Foundation.numberWithLongLong
import platform.MediaPlayer.MPMediaItem
import platform.MediaPlayer.MPMediaItemPropertyPersistentID
import platform.MediaPlayer.MPMediaPropertyPredicate
import platform.MediaPlayer.MPMediaQuery

// source : https://slack-chats.kotlinlang.org/t/12086405/hi-all-how-to-convert-ios-uiimage-to-compose-imagebitmap-in-
actual class TrackCoverLoader {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun load(artworkKey: String): ImageBitmap? {
        val predicate = MPMediaPropertyPredicate.predicateWithValue(
            NSNumber.numberWithLongLong(artworkKey.toLong()),
            MPMediaItemPropertyPersistentID
        )

        val query = MPMediaQuery()
        query.addFilterPredicate(predicate)

        val item = query.items?.firstOrNull() as? MPMediaItem
        val uiImage = item?.artwork
            ?.imageWithSize(CGSizeMake(512.0, 512.0))

        return uiImage?.toSkiaImage()?.toComposeImageBitmap()
    }
}

