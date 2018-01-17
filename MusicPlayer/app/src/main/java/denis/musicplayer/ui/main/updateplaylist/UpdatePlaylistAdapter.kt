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
    lateinit var presenter: UpdatePlaylistMvpPresenter<UpdatePlaylistMvpView>

    override fun onBindViewHolder(holder: UpdatePlaylistViewHolder, position: Int) {
        val playlist = presenter.getPlaylistByPosition(position)
        holder.onBind(playlist)
        holder.setBackground(position)
        holder.itemView.setOnClickListener {
            presenter.updatePlaylist(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UpdatePlaylistViewHolder =
            UpdatePlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_playlist, parent, false))

    override fun getItemCount(): Int = presenter.getArraySize()

    fun updateArray(array: ArrayList<Playlist>) {
        presenter.updateArray(array)
        notifyDataSetChanged()
    }
}