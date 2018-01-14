package denis.musicplayer.ui.playlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.utils.itemtouch.DragableAdapter
import kotlinx.android.synthetic.main.holder_moveable_playlist.view.*
import java.util.*

/**
 * Created by denis on 07/01/2018.
 */
class PlaylistAdapter(val context: Context) : DragableAdapter<PlaylistViewHolder>() {
    lateinit var presenter: PlaylistMvpPresenter<PlaylistMvpView>

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(presenter.getTrackByPosition(position))

        holder.itemView.reorder.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_DOWN) onStartDragListener.onStartDrag(holder)
            false
        }

        holder.itemView.setOnClickListener {
            presenter.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = presenter.getArraySize()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistViewHolder =
            PlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_moveable_playlist, parent, false))

    fun updateArray(tracks: ArrayList<Track>) {
        presenter.updateArray(tracks)
        notifyDataSetChanged()
    }

    override fun onItemMove(oldPos: Int, newPos: Int): Boolean {
        presenter.onItemMove(oldPos, newPos)
        presenter.swapArrayItems(oldPos, newPos)
        notifyItemMoved(oldPos, newPos)
        return true
    }

    override fun onItemSwipe(pos: Int) {
        presenter.onItemSwipe(presenter.getTrackByPosition(pos).id)
        presenter.removeItemAt(pos)
        notifyItemRemoved(pos)
    }
}