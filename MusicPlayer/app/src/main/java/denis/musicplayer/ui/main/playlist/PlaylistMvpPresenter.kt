package denis.musicplayer.ui.main.playlist

import denis.musicplayer.ui.main.base.MainBaseMvpPresenter
import denis.musicplayer.ui.main.base.MainBasePresenter

/**
 * Created by denis on 01/01/2018.
 */
interface PlaylistMvpPresenter<V : PlaylistMvpView> : MainBaseMvpPresenter<V> {
    fun getPlaylists()
}