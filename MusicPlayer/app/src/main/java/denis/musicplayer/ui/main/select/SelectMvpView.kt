package denis.musicplayer.ui.main.select

import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 06/01/2018.
 */
interface SelectMvpView : MvpView {
    fun updateCount(count: Int)
}