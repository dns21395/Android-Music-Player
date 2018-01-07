package denis.musicplayer.ui.main.playlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class MainPlaylistAdapter(val context: Context) : RecyclerView.Adapter<MainPlaylistViewHolder>() {

    lateinit var callback: Callback

    private var array = ArrayList<Playlist>()

    override fun onBindViewHolder(holder: MainPlaylistViewHolder, position: Int) {
        holder.onBind(array[position])
        holder.setBackground(position)
        holder.itemView.setOnClickListener {
            val playlist = array[position]
            callback.onPlaylistClicked(playlist.id, playlist.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainPlaylistViewHolder =
            MainPlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_playlist, parent, false))

    override fun getItemCount(): Int = array.size

    fun updateArray(array: ArrayList<Playlist>) {
        this.array = array
        notifyDataSetChanged()
    }

    interface Callback  {
        fun onPlaylistClicked(id: Long, title: String)
    }
}