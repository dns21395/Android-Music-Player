package denis.musicplayer.ui.main.base

import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.MainActivity

/**
 * Created by denis on 31/12/2017.
 */
abstract class MainBaseFragment: BaseFragment(), MainBaseMvpView {
    override fun showSelectFragment() {
        (activity as MainActivity).replaceFragment((activity as MainActivity).selectFragment)
    }

    override fun hideSelectFragment() {
        (activity as MainActivity).replaceFragment((activity as MainActivity).playerFragment)
    }
}