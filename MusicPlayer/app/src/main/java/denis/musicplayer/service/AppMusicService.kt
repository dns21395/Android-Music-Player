package denis.musicplayer.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import denis.musicplayer.App
import denis.musicplayer.di.ApplicationContext
import denis.musicplayer.service.music.MusicManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by denis on 10/01/2018.
 */
@Singleton
class AppMusicService
    @Inject constructor(@ApplicationContext val context: Context,
                        val musicManager: MusicManager) : Service(), MusicService {


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, AppMusicService::class.java)
        }

        fun start(context: Context) {
            val starter = Intent(context, AppMusicService::class.java)
            context.startService(starter)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, AppMusicService::class.java))
        }

        fun isRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (service.equals(AppMusicService::class.java)) {
                    return true
                }
            }
            return false
        }
    }



    override fun onCreate() {
        super.onCreate()

        (application as App).applicationComponent.inject(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}