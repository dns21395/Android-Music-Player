package com.densis.musicplayer.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import musicplayer.composeapp.generated.resources.Res
import musicplayer.composeapp.generated.resources.ic_album_cover
import org.jetbrains.compose.resources.painterResource

@Composable
fun Cover(
    cover: ImageBitmap?,
    imageSize: Dp,
    emptyIconSize: Dp,
) {
    if (cover != null) {
        Image(
            bitmap = cover,
            contentDescription = null,
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(imageSize)
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_album_cover),
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(emptyIconSize)
            )
        }
    }
}