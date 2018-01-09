package denis.musicplayer.ui.main.category.categoryfragment

import android.view.View
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_track.view.*

/**
 * Created by denis on 09/01/2018.
 */
class CategoryFragmentViewHolder(itemView: View) : MainBaseViewHolder<Track>(itemView) {
    override fun onBind(item: Track) = with(itemView) {
        trackName.text = item.title
        artist.text = item.artist
        duration.text = item.duration
    }
}