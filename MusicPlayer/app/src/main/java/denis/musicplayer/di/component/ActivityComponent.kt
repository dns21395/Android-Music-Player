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
import denis.musicplayer.ui.main.playlist.MainPlaylistFragment
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.main.track.TrackFragment
import denis.musicplayer.ui.permission.PermissionActivity
import denis.musicplayer.ui.player.fragment.PlayerFragment
import denis.musicplayer.ui.playlist.PlaylistActivity
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
    fun inject(playlistActivity: PlaylistActivity)

    // Fragment
    fun inject(mainContentFragment: MainContentFragment)
    fun inject(trackFragment: TrackFragment)
    fun inject(albumFragment: AlbumFragment)
    fun inject(mainPlaylistFragment: MainPlaylistFragment)
    fun inject(artistFragment: ArtistFragment)
    fun inject(genreFragment: GenreFragment)
    fun inject(selectFragment: SelectFragment)
    fun inject(playerFragment: PlayerFragment)

    // Dialog
    fun inject(addPlaylistDialog: AddPlaylistDialog)
}