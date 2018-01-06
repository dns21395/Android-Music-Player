package denis.musicplayer.ui.main.playlist

import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.MvpView
import denis.musicplayer.ui.main.base.MainBaseMvpView

/**
 * Created by denis on 01/01/2018.
 */
interface MainPlaylistMvpView : MvpView {
    fun updateArray(array: ArrayList<Playlist>)
}