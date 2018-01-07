package denis.musicplayer.ui.main.addplaylist

import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 03/01/2018.
 */
interface AddPlaylistMvpPresenter<V : AddPlaylistMvpView> : MvpPresenter<V> {
    fun createPlaylist(name: String)
}