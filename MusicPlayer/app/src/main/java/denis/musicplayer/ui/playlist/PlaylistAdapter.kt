package denis.musicplayer.ui.playlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track

/**
 * Created by denis on 07/01/2018.
 */
class PlaylistAdapter(val context: Context) : RecyclerView.Adapter<PlaylistViewHolder>() {
    val array = ArrayList<Track>()

    override fun onBindViewHolder(holder: PlaylistViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistViewHolder =
            PlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_moveable_playlist, parent, false))
}