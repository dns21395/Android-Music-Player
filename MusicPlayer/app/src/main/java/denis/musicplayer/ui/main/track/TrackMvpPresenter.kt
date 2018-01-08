package denis.musicplayer.ui.main.track

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 31/12/2017.
 */
interface TrackMvpPresenter<V: TrackMvpView> : MainBaseMvpPresenter<V, Track> {
}