package denis.musicplayer.di.component

import dagger.Component
import denis.musicplayer.di.PerActivity
import denis.musicplayer.di.module.ActivityModule
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.ui.main.add_playlist.AddPlaylistDialog
import denis.musicplayer.ui.main.album.AlbumFragment
import denis.musicplayer.ui.main.artist.ArtistFragment
import denis.musicplayer.ui.main.genre.GenreFragment
import denis.musicplayer.ui.main.main.MainContentFragment
import denis.musicplayer.ui.main.playlist.PlaylistFragment
import denis.musicplayer.ui.main.track.TrackFragment
import denis.musicplayer.ui.permission.PermissionActivity
import denis.musicplayer.ui.splash.SplashActivity

/**
 * Created by denis on 30/12/2017.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    // Activity
    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(permissionActivity: PermissionActivity)

    // Fragment
    fun inject(mainContentFragment: MainContentFragment)
    fun inject(trackFragment: TrackFragment)
    fun inject(albumFragment: AlbumFragment)
    fun inject(playlistFragment: PlaylistFragment)
    fun inject(artistFragment: ArtistFragment)
    fun inject(genreFragment: GenreFragment)

    // Dialog
    fun inject(addPlaylistDialog: AddPlaylistDialog)
}