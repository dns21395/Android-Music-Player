package com.densis.musicplayer.player.presentation.store

sealed class PlayerCommand {
    object GetTrack : PlayerCommand()
}