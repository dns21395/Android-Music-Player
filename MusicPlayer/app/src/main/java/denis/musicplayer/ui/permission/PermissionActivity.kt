package denis.musicplayer.ui.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_permission.*
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class PermissionActivity : BaseActivity(), PermissionMvpView {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, PermissionActivity::class.java)
    }

    @Inject lateinit var presenter: PermissionMvpPresenter<PermissionMvpView>

    private val TAG = "PermissionsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        transparentStatusBar()

        setGivePermissionButton()

    }

    private fun setGivePermissionButton() {
        givePermission.background = ContextCompat.getDrawable(this, R.drawable.outline_button)
        givePermission.setOnClickListener { openPermissionsActivity() }
    }

    override fun openPermissionsActivity() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri

        startActivityForResult(intent, 0);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "${PermissionUtils.isPermissionsGranted(this)}")

        if(PermissionUtils.isPermissionsGranted(this))
            openMainActivity()
    }

    override fun openMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
    }
}