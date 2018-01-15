package denis.musicplayer.ui.main

import android.support.v4.app.Fragment
import denis.musicplayer.ui.base.MvpView
import denis.musicplayer.ui.main.base.MainBaseActivityMvpView

/**
 * Created by denis on 30/12/2017.
 */
interface MainMvpView : MainBaseActivityMvpView {
    fun replaceFragment(fragment: Fragment)
    fun showSelectFragment()
    fun hideSelectFragment()
}