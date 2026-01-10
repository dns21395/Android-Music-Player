package com.densis.musicplayer.data

import android.util.Log

actual class AppLogger {
    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}