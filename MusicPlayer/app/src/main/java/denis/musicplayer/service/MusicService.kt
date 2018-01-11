package denis.musicplayer.service

import android.app.Notification

/**
 * Created by denis on 10/01/2018.
 */
interface MusicService {
    fun showNotificationForeground(notification: Notification)
    fun stopService()
}