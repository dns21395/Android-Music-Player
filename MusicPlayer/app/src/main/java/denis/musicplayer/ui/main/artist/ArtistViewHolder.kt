package denis.musicplayer.ui.main.artist

import android.view.View
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Artist
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_artist.view.*

/**
 * Created by denis on 02/01/2018.
 */
class ArtistViewHolder(itemView: View) : MainBaseViewHolder<Artist>(itemView) {
    override fun onBind(item: Artist) = with(itemView) {
        artist.text = item.artist
        albumCount.text = resources.getQuantityString(R.plurals.album_count, item.albumCount.toInt(), item.albumCount)
        trackCount.text = resources.getQuantityString(R.plurals.track_count, item.trackCount.toInt(), item.trackCount)
    }
}