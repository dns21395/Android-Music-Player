package denis.musicplayer.ui.playlist

import android.support.v7.widget.RecyclerView
import android.view.View
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.utils.itemtouch.DragableHolder
import kotlinx.android.synthetic.main.holder_moveable_playlist.view.*

/**
 * Created by denis on 07/01/2018.
 */
class PlaylistViewHolder(itemView: View) : DragableHolder(itemView) {
    fun onBind(track: Track) = with(itemView) {
        title.text = track.title
        artist.text = track.artist
        duration.text = track.duration
    }
}