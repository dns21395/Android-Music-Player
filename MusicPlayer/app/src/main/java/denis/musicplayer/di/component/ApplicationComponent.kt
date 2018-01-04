package denis.musicplayer.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import denis.musicplayer.App
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ApplicationContext
import denis.musicplayer.di.module.ApplicationModule
import denis.musicplayer.ui.main.base.MainRxBus
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by denis on 30/12/2017.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: App)

    @ApplicationContext
    fun provideContext(): Context
    fun provideApplication(): Application
    fun provideCompositeDisposable(): CompositeDisposable
    fun provideDataManager(): DataManager
}