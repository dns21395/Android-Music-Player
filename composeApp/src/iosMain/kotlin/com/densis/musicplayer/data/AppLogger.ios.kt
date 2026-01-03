package com.densis.musicplayer.data

import platform.Foundation.NSLog

actual class AppLogger {
    actual fun d(tag: String, message: String) {
        NSLog("DEBUG: [$tag] $message")
    }
}