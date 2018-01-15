package denis.musicplayer.ui.main.base

import denis.musicplayer.data.media.model.Track


/**
 * Created by denis on 31/12/2017.
 */
interface MainBaseFragmentMvpView<A: Any> : MainBaseActivityMvpView {
    fun updateArray(array: ArrayList<A>)

    fun notifyDataSetChanged()

    fun openCategoryTracksActivity(tracks: ArrayList<Track>, title: String)
}