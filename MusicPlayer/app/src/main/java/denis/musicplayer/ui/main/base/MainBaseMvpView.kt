package denis.musicplayer.ui.main.base

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 31/12/2017.
 */
interface MainBaseMvpView : MvpView {
    fun showSelectFragment()
    fun hideSelectFragment()
    fun updateCountSelectFragment(count: Int)
    fun showUpdatePlaylist(array: ArrayList<Track>)
}