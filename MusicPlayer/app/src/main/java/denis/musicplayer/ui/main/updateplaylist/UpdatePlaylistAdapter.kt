package denis.musicplayer.ui.main.updateplaylist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist

/**
 * Created by denis on 07/01/2018.
 */
class UpdatePlaylistAdapter(val context: Context) : RecyclerView.Adapter<UpdatePlaylistViewHolder>() {
    lateinit var callback: Callback

    private var array = ArrayList<Playlist>()

    override fun onBindViewHolder(holder: UpdatePlaylistViewHolder, position: Int) {
        holder.onBind(array[position])
        holder.setBackground(position)
        holder.itemView.setOnClickListener {
            val playlist = array[position]
            callback.onPlaylistChose(playlist.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UpdatePlaylistViewHolder =
            UpdatePlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_playlist, parent, false))

    override fun getItemCount(): Int = array.size

    fun updateArray(array: ArrayList<Playlist>) {
        this.array = array
        notifyDataSetChanged()
    }

    interface Callback {
        fun onPlaylistChose(id: Int)
    }
}