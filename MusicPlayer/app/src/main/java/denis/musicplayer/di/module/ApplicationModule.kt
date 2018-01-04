package denis.musicplayer.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import denis.musicplayer.data.AppDataManager
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.AppMediaManager
import denis.musicplayer.data.media.MediaManager
import denis.musicplayer.data.playlist.AppPlaylistManager
import denis.musicplayer.data.playlist.PlaylistManager
import denis.musicplayer.di.ApplicationContext
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by denis on 30/12/2017.
 */
@Module
class ApplicationModule(val application: Application) {
    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @Singleton
    fun providePlaylistManager(appPlaylistManager: AppPlaylistManager): PlaylistManager = appPlaylistManager

    @Provides
    @Singleton
    fun provideMediaManager(appMediaManager: AppMediaManager): MediaManager = appMediaManager

}