package denis.musicplayer.utils

import android.app.ActivityManager
import android.content.Context

/**
 * Created by denis on 11/01/2018.
 */
class CommonUtils {
    companion object {
        fun isRunning(context: Context, serviceClass: Class<*>): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.className)) {
                    return true
                }
            }
            return false
        }
    }
}