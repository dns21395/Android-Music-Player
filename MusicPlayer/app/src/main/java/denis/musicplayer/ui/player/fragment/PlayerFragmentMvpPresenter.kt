package denis.musicplayer.ui.player.fragment

import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 06/01/2018.
 */
interface PlayerFragmentMvpPresenter<V : PlayerFragmentMvpView> : MvpPresenter<V> {
    fun updateFragment()

    fun actionCondition()

    fun callAction()
}