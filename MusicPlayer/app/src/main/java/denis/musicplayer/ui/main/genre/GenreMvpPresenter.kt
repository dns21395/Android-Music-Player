package denis.musicplayer.ui.main.genre

import denis.musicplayer.ui.base.MvpPresenter
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 01/01/2018.
 */
interface GenreMvpPresenter<V : GenreMvpView> : MvpPresenter<V> {
    fun getGenres()
}