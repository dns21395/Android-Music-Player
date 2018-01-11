package denis.musicplayer.di.component

import dagger.Component
import denis.musicplayer.di.PerService
import denis.musicplayer.di.module.ServiceModule
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.MusicService

/**
 * Created by denis on 11/01/2018.
 */
@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ServiceModule::class))
interface ServiceComponent {
    fun inject(appMusicService: AppMusicService)

    fun provideMusicServce(): MusicService
}