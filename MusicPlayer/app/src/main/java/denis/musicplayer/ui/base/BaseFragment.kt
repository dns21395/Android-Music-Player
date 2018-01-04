package denis.musicplayer.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import denis.musicplayer.di.component.ActivityComponent
import denis.musicplayer.utils.KeyboardUtils

/**
 * Created by denis on 30/12/2017.
 */
abstract class BaseFragment : Fragment(), MvpView {
    var activity: BaseActivity? = null
    var activityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseActivity) {
            activity = context
            activityComponent = activity?.activityComponent
        }
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(activity as Activity)
    }

    protected abstract fun setUp(view: View?)

}