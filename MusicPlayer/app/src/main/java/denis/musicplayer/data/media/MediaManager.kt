package denis.musicplayer.data.media

import denis.musicplayer.data.media.model.*
import denis.musicplayer.data.playlist.model.Playlist

/**
 * Created by denis on 01/01/2018.
 */
interface MediaManager {
    fun scanTracks(): ArrayList<Track>

    fun scanAlbums(): ArrayList<Album>
    fun scanAlbumTracks(albumID: Long): ArrayList<Track>
    fun getAlbumImagePath(albumID: Long): String?

    fun scanArtists(): ArrayList<Artist>
    fun scanArtistTracks(artistID: Long): ArrayList<Track>

    fun scanGenres(): ArrayList<Genre>
    fun scanGenreTracks(genreID: Long): ArrayList<Track>
}