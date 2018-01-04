package denis.musicplayer.ui.splash

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.ui.permission.PermissionActivity
import denis.musicplayer.utils.PermissionUtils
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class SplashActivity : BaseActivity(), SplashMvpView {

    private val CODE_PERMISSION = 1337

    private val TAG = "SplashActivity"

    @Inject lateinit var presenter: SplashMvpPresenter<SplashMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        transparentStatusBar()

        checkPermission()
    }

    private fun checkPermission() {
        when(PermissionUtils.isPermissionsGranted(this)) {
            true -> openMainActivity()
            false -> requestPermissions()
        }
    }

    override fun openMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    override fun openPermissionActivity() {
        startActivity(PermissionActivity.getStartIntent(this))
        finish()
    }

    override fun requestPermissions() {
        ActivityCompat.requestPermissions(this, PermissionUtils.permissions, CODE_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode == CODE_PERMISSION && PermissionUtils.isPermissionsGranted(this)) {
            true -> openMainActivity()
            else -> openPermissionActivity()
        }
    }
}