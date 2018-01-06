package denis.musicplayer.ui.base

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.readystatesoftware.systembartint.SystemBarTintManager
import denis.musicplayer.di.component.ActivityComponent
import denis.musicplayer.di.component.DaggerActivityComponent
import denis.musicplayer.di.module.ActivityModule
import denis.musicplayer.utils.KeyboardUtils
import denis.musicplayer.utils.app

/**
 * Created by denis on 30/12/2017.
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {
    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(app.applicationComponent)
                .build()
    }

    protected abstract fun setUp()

    fun transparentStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        val tintManager = SystemBarTintManager(this)
        // enable status bar tint
        tintManager.isStatusBarTintEnabled = true
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true)
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }






}