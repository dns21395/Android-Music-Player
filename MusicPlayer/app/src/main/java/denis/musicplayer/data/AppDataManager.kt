package denis.musicplayer.data

import android.content.Context
import denis.musicplayer.data.media.MediaManager
import denis.musicplayer.data.media.model.*
import denis.musicplayer.data.playlist.PlaylistManager
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by denis on 01/01/2018.
 */
@Singleton
class AppDataManager
    @Inject constructor(@ApplicationContext val context: Context,
                        private val mediaManager: MediaManager,
                        private val playlistManager: PlaylistManager)
    : DataManager {

    // Media

    override fun scanAlbums(): ArrayList<Album> =
            mediaManager.scanAlbums()

    override fun scanTracks(): ArrayList<Track> =
            mediaManager.scanTracks()

    override fun scanArtists(): ArrayList<Artist> =
            mediaManager.scanArtists()

    override fun scanGenres(): ArrayList<Genre> =
            mediaManager.scanGenres()

    // Playlist

    override fun scanPlaylist(): ArrayList<Playlist> =
            playlistManager.scanPlaylist()

    override fun createPlaylist(name: String) {
        playlistManager.createPlaylist(name)
    }

    override fun deletePlaylist(id: Int) {
        playlistManager.deletePlaylist(id)
    }



}