package denis.musicplayer.ui.main.playlist

import android.view.View
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_playlist.view.*

/**
 * Created by denis on 02/01/2018.
 */
class PlaylistViewHolder(itemView: View) : MainBaseViewHolder(itemView) {
    private var playlist: Playlist? = null

    fun onBind(playlist: Playlist) = with(itemView) {
        this@PlaylistViewHolder.playlist = playlist

        playlistName.text = playlist.name
    }
}