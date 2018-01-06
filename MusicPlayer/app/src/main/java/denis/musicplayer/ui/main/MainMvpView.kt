package denis.musicplayer.ui.main

import android.support.v4.app.Fragment
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 30/12/2017.
 */
interface MainMvpView : MvpView {
    fun replaceFragment(fragment: Fragment)
}