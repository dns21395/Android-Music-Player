package denis.musicplayer.ui.main.playlist

import android.content.Context
import android.telecom.Call
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class MainPlaylistAdapter(val context: Context) : MainBaseAdapter<MainPlaylistViewHolder, Playlist>() {

    lateinit var callback: Callback

    override fun onBindViewHolder(holder: MainPlaylistViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(array[position])
        holder.itemView.setOnClickListener {
            val playlist = array[position]
            callback.onPlaylistClicked(playlist.id, playlist.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainPlaylistViewHolder =
            MainPlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_playlist, parent, false))

    interface Callback {
        fun onPlaylistClicked(id: Int, title: String)
    }
}