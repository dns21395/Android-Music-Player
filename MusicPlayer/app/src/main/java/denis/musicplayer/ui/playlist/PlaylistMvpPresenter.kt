package denis.musicplayer.ui.playlist

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 04/01/2018.
 */
interface PlaylistMvpPresenter<V: PlaylistMvpView> : MvpPresenter<V> {
    fun deletePlaylist(id: Long)

    fun getTracks(id: Long)

    fun onItemMove(playlistId: Long, oldPos: Int, newPos: Int)

    fun onItemSwipe(playlistId: Long, trackId: Long)

    // Array

    fun updateArray(array: ArrayList<Track>)

    fun getTrackByPosition(position: Int): Track

    fun getArraySize(): Int

    fun swapArrayItems(oldPos: Int, newPos: Int)

    fun removeItemAt(position: Int)


}