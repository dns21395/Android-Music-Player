package denis.musicplayer.ui.main.updateplaylist

import android.os.Bundle
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 06/01/2018.
 */
interface UpdatePlaylistMvpPresenter<V : UpdatePlaylistMvpView> : MvpPresenter<V> {
    fun getPlaylist()

    fun getPlaylistByPosition(position: Int): Playlist

    fun getArraySize(): Int

    fun setArrayTracks(bundle: Bundle)

    fun updateArray(array: ArrayList<Playlist>)

    fun updatePlaylist(position: Int)
}