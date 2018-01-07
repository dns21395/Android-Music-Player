package denis.musicplayer.data.playlist

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist

/**
 * Created by denis on 03/01/2018.
 */
interface PlaylistManager {
    fun scanPlaylist(): ArrayList<Playlist>

    fun createPlaylist(name: String)

    fun deletePlaylist(id: Long)

    fun addTracksToPlaylist(id: Long, tracks: ArrayList<Track>)

    fun deletePlaylistTrack(playlistId: Long, trackId: Long)

    fun playlistItemReorder(playlistId: Long, oldPos: Int, newPos: Int)

    fun getPlaylistTracks(id: Long): ArrayList<Track>

}