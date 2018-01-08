package denis.musicplayer.ui.main.album

import denis.musicplayer.data.media.model.Album
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.MvpView
import denis.musicplayer.ui.main.base.MainBaseMvpView

/**
 * Created by denis on 01/01/2018.
 */
interface AlbumMvpView : MainBaseMvpView {
    fun updateArray(array: ArrayList<Album>)

    fun showUpdatePlaylist(array: ArrayList<Track>)

    fun getAlbumTracks()

    fun cancelSelecting()


}