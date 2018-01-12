package denis.musicplayer.ui.player.fragment

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 06/01/2018.
 */
interface PlayerFragmentMvpView : MvpView {
    fun updateFragment(track: Track)

    fun updateCover(coverPath: String?)
}