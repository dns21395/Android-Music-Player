package denis.musicplayer.ui.main.category.categoryfragment

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 09/01/2018.
 */
interface CategoryFragmentMvpPresenter<V: CategoryFragmentMvpView> : MainBaseMvpPresenter<V, Track> {
}