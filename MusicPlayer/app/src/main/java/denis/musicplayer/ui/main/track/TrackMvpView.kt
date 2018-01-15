package denis.musicplayer.ui.main.track

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragmentMvpView

/**
 * Created by denis on 31/12/2017.
 */
interface TrackMvpView : MainBaseFragmentMvpView<Track> {
    fun openPlayerActivity()
}