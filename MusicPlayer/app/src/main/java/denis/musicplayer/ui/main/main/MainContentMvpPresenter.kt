package denis.musicplayer.ui.main.main

import denis.musicplayer.data.select.EnumSelectManager
import denis.musicplayer.ui.base.MvpPresenter
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 01/01/2018.
 */
interface MainContentMvpPresenter<V : MainContentMvpView> : MvpPresenter<V> {
    fun callActionClear()
}