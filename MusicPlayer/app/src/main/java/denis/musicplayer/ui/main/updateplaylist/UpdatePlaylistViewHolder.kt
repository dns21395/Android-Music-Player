package denis.musicplayer.ui.main.updateplaylist

import android.view.View
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_playlist.view.*

/**
 * Created by denis on 07/01/2018.
 */
class UpdatePlaylistViewHolder(itemView: View) : MainBaseViewHolder<Playlist>(itemView) {

    override fun onBind(item: Playlist) = with(itemView) {

        playlistName.text = item.name
    }
}