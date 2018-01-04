package denis.musicplayer.ui.splash

import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 30/12/2017.
 */
interface SplashMvpView : MvpView {
    fun openMainActivity()

    fun openPermissionActivity()

    fun requestPermissions()
}