package com.densis.musicplayer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform