package denis.musicplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import denis.musicplayer.App
import denis.musicplayer.di.component.DaggerServiceComponent
import denis.musicplayer.di.component.ServiceComponent
import denis.musicplayer.di.module.ServiceModule
import denis.musicplayer.service.music.MusicManager
import javax.inject.Inject

/**
 * Created by denis on 10/01/2018.
 */
class MusicService : Service() {

    private val serviceComponent: ServiceComponent by lazy {
        DaggerServiceComponent
                .builder()
                .serviceModule(ServiceModule(this))
                .applicationComponent((application as App).applicationComponent)
                .build()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MusicService::class.java)
        }

        fun start(context: Context) {
            val starter = Intent(context, MusicService::class.java)
            context.startService(starter)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, MusicService::class.java))
        }
    }

    override fun onCreate() {
        super.onCreate()

        serviceComponent.inject(this)

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