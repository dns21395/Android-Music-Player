package denis.musicplayer

import android.app.Application
import denis.musicplayer.di.component.ApplicationComponent
import denis.musicplayer.di.component.DaggerApplicationComponent
import denis.musicplayer.di.module.ApplicationModule

/**
 * Created by denis on 29/12/2017.
 */
class App : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}