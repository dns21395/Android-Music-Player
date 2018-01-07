package denis.musicplayer.ui.playlist

import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 04/01/2018.
 */
interface PlaylistMvpPresenter<V: PlaylistMvpView> : MvpPresenter<V> {
    fun deletePlaylist(id: Long)

    fun getTracks(id: Long)

    fun onItemMove(playlistId: Long, oldPos: Int, newPos: Int)

    fun onItemSwipe(playlistId: Long, trackId: Long)
}