package denis.musicplayer.ui.main.updateplaylist

import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 06/01/2018.
 */
interface UpdatePlaylistMvpView : MvpView {
    fun updateArray(array: ArrayList<Playlist>)

    fun finishDialog(toastText: String)
}