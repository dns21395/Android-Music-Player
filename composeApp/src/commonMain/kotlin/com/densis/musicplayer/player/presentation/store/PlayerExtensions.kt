package com.densis.musicplayer.player.presentation.store

fun Long.toTime(): String {
    val totalSeconds = (this / 1000L).coerceAtLeast(0L)
    val minutes = (totalSeconds / 60L).toInt()
    val seconds = (totalSeconds % 60L).toInt()

    val mm = if (minutes < 10) "0$minutes" else "$minutes"
    val ss = if (seconds < 10) "0$seconds" else "$seconds"

    return "$mm:$ss"
}