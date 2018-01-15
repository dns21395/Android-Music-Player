package denis.musicplayer.ui.playlist

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 04/01/2018.
 */
interface PlaylistMvpView : MvpView {
    fun onPlaylistDeleted()

    fun updateArray(array: ArrayList<Track>)

    fun openPlayerActivity()
}