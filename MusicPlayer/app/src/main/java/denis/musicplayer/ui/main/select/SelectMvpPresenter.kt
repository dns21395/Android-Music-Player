package denis.musicplayer.ui.main.select

import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 06/01/2018.
 */
interface SelectMvpPresenter<V: SelectMvpView> : MvpPresenter<V> {
    fun sendMessageToUpdatePlaylist()

    fun cancelSelecting()
}