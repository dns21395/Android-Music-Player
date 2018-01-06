package denis.musicplayer.ui.main.track

import android.view.View
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_track.view.*

/**
 * Created by denis on 02/01/2018.
 */
class TrackViewHolder(itemView: View) : MainBaseViewHolder(itemView) {
    fun onBind(track: Track) = with(itemView) {
        trackName.text = track.title
        artist.text = track.artist
        duration.text = track.duration
    }
}