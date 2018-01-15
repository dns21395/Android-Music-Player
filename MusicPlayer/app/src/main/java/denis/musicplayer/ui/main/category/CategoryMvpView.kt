package denis.musicplayer.ui.main.category

import android.support.v4.app.Fragment
import denis.musicplayer.ui.main.base.MainBaseActivityMvpView

/**
 * Created by denis on 09/01/2018.
 */
interface CategoryMvpView : MainBaseActivityMvpView  {
    fun showSelectFragment()
    fun hideSelectFragment()
    fun replaceFragment(fragment: Fragment)

}