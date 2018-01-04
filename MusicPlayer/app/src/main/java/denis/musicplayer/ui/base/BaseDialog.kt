package denis.musicplayer.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import denis.musicplayer.R
import denis.musicplayer.di.component.ActivityComponent
import denis.musicplayer.utils.KeyboardUtils

/**
 * Created by denis on 03/01/2018.
 */
abstract class BaseDialog : DialogFragment(), DialogMvpView {
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

    override fun dismissDialog(tag: String) {
        dismiss()
    }

}