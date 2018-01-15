package denis.musicplayer.ui.main.base

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 09/01/2018.
 */
interface MainBaseActivityMvpView : MvpView {
    fun showUpdatePlaylist(array: ArrayList<Track>)
}
