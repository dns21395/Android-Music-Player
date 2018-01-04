package denis.musicplayer.utils

import android.app.Activity
import denis.musicplayer.App

/**
 * Created by denis on 30/12/2017.
 */
val Activity.app: App
    get() = application as App