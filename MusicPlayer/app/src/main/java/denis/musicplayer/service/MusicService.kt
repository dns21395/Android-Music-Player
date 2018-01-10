package denis.musicplayer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import denis.musicplayer.App
import denis.musicplayer.di.component.DaggerServiceComponent
import denis.musicplayer.di.component.ServiceComponent
import denis.musicplayer.di.module.ServiceModule

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

    override fun onCreate() {
        super.onCreate()

        serviceComponent.inject(this)

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}