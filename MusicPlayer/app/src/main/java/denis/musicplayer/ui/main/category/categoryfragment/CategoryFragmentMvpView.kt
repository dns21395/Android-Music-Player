package denis.musicplayer.ui.main.category.categoryfragment

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragmentMvpView

/**
 * Created by denis on 09/01/2018.
 */
interface CategoryFragmentMvpView : MainBaseFragmentMvpView<Track> {
    fun openPlayerActivity()
}