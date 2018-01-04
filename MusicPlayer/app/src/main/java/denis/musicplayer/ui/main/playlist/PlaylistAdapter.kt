package denis.musicplayer.ui.main.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class PlaylistAdapter(val context: Context) : MainBaseAdapter<PlaylistViewHolder, Playlist>() {
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(array[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistViewHolder =
            PlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_playlist, parent, false))
}