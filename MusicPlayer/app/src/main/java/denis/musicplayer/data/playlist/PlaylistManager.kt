package denis.musicplayer.data.playlist

import denis.musicplayer.data.playlist.model.Playlist

/**
 * Created by denis on 03/01/2018.
 */
interface PlaylistManager {
    fun scanPlaylist(): ArrayList<Playlist>

    fun createPlaylist(name: String)

    fun deletePlaylist(id: Int)
}