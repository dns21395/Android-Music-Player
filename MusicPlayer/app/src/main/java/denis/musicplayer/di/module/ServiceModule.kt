package denis.musicplayer.di.module

import dagger.Module
import dagger.Provides
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.MusicService

/**
 * Created by denis on 11/01/2018.
 */
@Module
class ServiceModule(val musicService: AppMusicService) {

    @Provides
    fun provideMusicService(): MusicService = musicService


}