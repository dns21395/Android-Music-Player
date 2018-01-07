package denis.musicplayer.ui.main.updateplaylist

import android.os.Bundle
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 06/01/2018.
 */
interface UpdatePlaylistMvpPresenter<V : UpdatePlaylistMvpView> : MvpPresenter<V> {
    fun getPlaylist()

    fun updatePlaylist(bundle: Bundle, playlistId: Long)
}