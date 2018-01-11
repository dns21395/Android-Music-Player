package denis.musicplayer.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import denis.musicplayer.App
import denis.musicplayer.di.ApplicationContext
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.utils.CommonUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by denis on 10/01/2018.
 */
@Singleton
class AppMusicService : Service(), MusicService {

    @field:[Inject ApplicationContext] lateinit var context: Context
    @Inject lateinit var musicManager: MusicManager

    companion object {
        private val TAG = "AppMusicService"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AppMusicService::class.java)
        }

        fun start(context: Context) {
            if(!CommonUtils.isRunning(context, AppMusicService::class.java)) {
                Log.d(TAG, "service started")
                val starter = Intent(context, AppMusicService::class.java)
                context.startService(starter)
            }
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, AppMusicService::class.java))
        }


    }

    override fun onCreate() {
        super.onCreate()

        (application as App).applicationComponent.inject(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun buildNotification() {

    }

}