package denis.musicplayer.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

/**
 * Created by denis on 30/12/2017.
 */
class PermissionUtils {
    companion object {
        private val TAG = "PermissionsUtils"

        val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE,
                                  android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                  android.Manifest.permission.READ_EXTERNAL_STORAGE)



        fun isPermissionsGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23) {
                permissions
                        .filter { context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
                        .forEach { Log.d(TAG, "$it not granted"); return false }
                return true
            }
            return true
        }
    }


}