package denis.musicplayer.ui.main.track

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseMvpView

/**
 * Created by denis on 31/12/2017.
 */
interface TrackMvpView : MainBaseMvpView {
    fun updateArray(array: ArrayList<Track>)
}