package denis.musicplayer.ui.main.track

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class TrackAdapter(val context: Context) : MainBaseAdapter<TrackViewHolder, Track>() {
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.onBind(array[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackViewHolder =
            TrackViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_track, parent, false))
}