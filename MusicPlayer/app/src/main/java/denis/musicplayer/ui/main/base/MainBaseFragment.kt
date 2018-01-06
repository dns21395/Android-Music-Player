package denis.musicplayer.ui.main.base

import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.ui.main.select.SelectFragment
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * Created by denis on 31/12/2017.
 */
abstract class MainBaseFragment: BaseFragment(), MainBaseMvpView {

    private val TAG = "MainBaseFragment"

    override fun showSelectFragment() {
        (activity as MainActivity).replaceFragment((activity as MainActivity).selectFragment)
    }

    override fun hideSelectFragment() {
        (activity as MainActivity).replaceFragment((activity as MainActivity).playerFragment)
    }

    override fun updateCountSelectFragment(count: Int) {
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.frameLayout) as SelectFragment?

        Log.d(TAG, "updateCount $fragment")

        fragment?.updateCount(count)

    }
}