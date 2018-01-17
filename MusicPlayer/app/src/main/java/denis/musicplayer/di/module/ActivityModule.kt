package denis.musicplayer.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import denis.musicplayer.data.main.AppMainManager
import denis.musicplayer.data.main.MainManager
import denis.musicplayer.data.select.AppSelectManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.di.PerActivity
import denis.musicplayer.ui.main.MainAdapter
import denis.musicplayer.ui.main.MainMvpPresenter
import denis.musicplayer.ui.main.MainMvpView
import denis.musicplayer.ui.main.MainPresenter
import denis.musicplayer.ui.main.addplaylist.AddPlaylistMvpPresenter
import denis.musicplayer.ui.main.addplaylist.AddPlaylistMvpView
import denis.musicplayer.ui.main.addplaylist.AddPlaylistPresenter
import denis.musicplayer.ui.main.album.AlbumAdapter
import denis.musicplayer.ui.main.album.AlbumMvpPresenter
import denis.musicplayer.ui.main.album.AlbumMvpView
import denis.musicplayer.ui.main.album.AlbumPresenter
import denis.musicplayer.ui.main.artist.ArtistAdapter
import denis.musicplayer.ui.main.artist.ArtistMvpPresenter
import denis.musicplayer.ui.main.artist.ArtistMvpView
import denis.musicplayer.ui.main.artist.ArtistPresenter
import denis.musicplayer.ui.main.base.MainRxBus
import denis.musicplayer.ui.main.category.CategoryMvpPresenter
import denis.musicplayer.ui.main.category.CategoryMvpView
import denis.musicplayer.ui.main.category.CategoryPresenter
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragmentAdapter
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragmentMvpPresenter
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragmentMvpView
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragmentPresenter
import denis.musicplayer.ui.main.genre.GenreAdapter
import denis.musicplayer.ui.main.genre.GenreMvpPresenter
import denis.musicplayer.ui.main.genre.GenreMvpView
import denis.musicplayer.ui.main.genre.GenrePresenter
import denis.musicplayer.ui.main.main.MainContentMvpPresenter
import denis.musicplayer.ui.main.main.MainContentMvpView
import denis.musicplayer.ui.main.main.MainContentPresenter
import denis.musicplayer.ui.main.playlist.*
import denis.musicplayer.ui.main.select.SelectMvpPresenter
import denis.musicplayer.ui.main.select.SelectMvpView
import denis.musicplayer.ui.main.select.SelectPresenter
import denis.musicplayer.ui.main.track.TrackAdapter
import denis.musicplayer.ui.main.track.TrackMvpPresenter
import denis.musicplayer.ui.main.track.TrackMvpView
import denis.musicplayer.ui.main.track.TrackPresenter
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistAdapter
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistMvpPresenter
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistMvpView
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistPresenter
import denis.musicplayer.ui.permission.PermissionMvpPresenter
import denis.musicplayer.ui.permission.PermissionMvpView
import denis.musicplayer.ui.permission.PermissionPresenter
import denis.musicplayer.ui.player.PlayerActivityMvpPresenter
import denis.musicplayer.ui.player.PlayerActivityMvpView
import denis.musicplayer.ui.player.PlayerActivityPresenter
import denis.musicplayer.ui.player.fragment.PlayerFragmentMvpPresenter
import denis.musicplayer.ui.player.fragment.PlayerFragmentMvpView
import denis.musicplayer.ui.player.fragment.PlayerFragmentPresenter
import denis.musicplayer.ui.playlist.PlaylistAdapter
import denis.musicplayer.ui.playlist.PlaylistMvpPresenter
import denis.musicplayer.ui.playlist.PlaylistMvpView
import denis.musicplayer.ui.playlist.PlaylistPresenter
import denis.musicplayer.ui.splash.SplashMvpPresenter
import denis.musicplayer.ui.splash.SplashMvpView
import denis.musicplayer.ui.splash.SplashPresenter

/**
 * Created by denis on 30/12/2017.
 */
@Module
class ActivityModule(val activity: AppCompatActivity) {
    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideLinearLayoutManager(@ActivityContext context: Context): LinearLayoutManager = LinearLayoutManager(context)

    @Provides
    @PerActivity
    fun provideMainManager(appMainManager: AppMainManager): MainManager = appMainManager

    @Provides
    @PerActivity
    fun provideSelectManager(appSelectManager: AppSelectManager): SelectManager = appSelectManager

    // Activity

    @Provides
    @PerActivity
    fun provideSplashPresenter(presenter: SplashPresenter<SplashMvpView>): SplashMvpPresenter<SplashMvpView> = presenter

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpPresenter<MainMvpView> = presenter

    @Provides
    @PerActivity
    fun providePermissionPresenter(presenter: PermissionPresenter<PermissionMvpView>): PermissionMvpPresenter<PermissionMvpView> = presenter

    @Provides
    @PerActivity
    fun providePlaylistPresenter(presenter: PlaylistPresenter<PlaylistMvpView>): PlaylistMvpPresenter<PlaylistMvpView> = presenter

   @Provides
   @PerActivity
   fun provideCategoryPresenter(presenter: CategoryPresenter<CategoryMvpView>): CategoryMvpPresenter<CategoryMvpView> = presenter

    @Provides
    @PerActivity
    fun providePlayerActivityPresenter(presenter: PlayerActivityPresenter<PlayerActivityMvpView>): PlayerActivityMvpPresenter<PlayerActivityMvpView> = presenter

    // Fragment

    @Provides
    fun provideMainContentPresenter(presenter: MainContentPresenter<MainContentMvpView>): MainContentMvpPresenter<MainContentMvpView> = presenter

    @Provides
    fun provideTrackPresenter(presenter: TrackPresenter<TrackMvpView>): TrackMvpPresenter<TrackMvpView> = presenter

    @Provides
    fun provideAlbumPresenter(presenter: AlbumPresenter<AlbumMvpView>): AlbumMvpPresenter<AlbumMvpView> = presenter

    @Provides
    fun provideMainPlaylistPresenter(presenter: MainPlaylistPresenter<MainPlaylistMvpView>): MainPlaylistMvpPresenter<MainPlaylistMvpView> = presenter

    @Provides
    fun provideArtistPresenter(presenter: ArtistPresenter<ArtistMvpView>): ArtistMvpPresenter<ArtistMvpView> = presenter

    @Provides
    fun provideGenrePresenter(presenter: GenrePresenter<GenreMvpView>): GenreMvpPresenter<GenreMvpView> = presenter

    @Provides
    fun provideSelectPresenter(presenter: SelectPresenter<SelectMvpView>): SelectMvpPresenter<SelectMvpView> = presenter

    @Provides
    fun provideFragmentPlayerPresenter(presenter: PlayerFragmentPresenter<PlayerFragmentMvpView>): PlayerFragmentMvpPresenter<PlayerFragmentMvpView> = presenter

    @Provides
    fun provideCategoryFragmentPresenter(presenter: CategoryFragmentPresenter<CategoryFragmentMvpView>): CategoryFragmentMvpPresenter<CategoryFragmentMvpView> = presenter

    // / Dialog

    @Provides
    fun provideAddDialogPresenter(presenter: AddPlaylistPresenter<AddPlaylistMvpView>): AddPlaylistMvpPresenter<AddPlaylistMvpView> = presenter

    @Provides
    fun provideUpdatePlaylistPresenter(presenter: UpdatePlaylistPresenter<UpdatePlaylistMvpView>): UpdatePlaylistMvpPresenter<UpdatePlaylistMvpView> = presenter

    // Adapter

    @Provides
    fun provideMainAdapter(@ActivityContext context: Context) = MainAdapter(context, activity.supportFragmentManager)

    @Provides
    fun provideMainPlaylistAdapter(@ActivityContext context: Context): MainPlaylistAdapter = MainPlaylistAdapter(context)

    @Provides
    fun provideAlbumAdapter(@ActivityContext context: Context): AlbumAdapter = AlbumAdapter(context)

    @Provides
    fun provideGenreAdapter(@ActivityContext context: Context): GenreAdapter = GenreAdapter(context)

    @Provides
    fun provideTrackAdapter(@ActivityContext context: Context): TrackAdapter = TrackAdapter(context)

    @Provides
    fun provideArtistAdapter(@ActivityContext context: Context): ArtistAdapter = ArtistAdapter(context)

    @Provides
    fun provideUpdatePlaylistAdapter(@ActivityContext context: Context): UpdatePlaylistAdapter = UpdatePlaylistAdapter(context)

    @Provides
    fun providePlaylistAdapter(@ActivityContext context: Context): PlaylistAdapter = PlaylistAdapter(context)

    @Provides
    fun provideCategoryFragmentAdapter(@ActivityContext context: Context): CategoryFragmentAdapter = CategoryFragmentAdapter(context)









}