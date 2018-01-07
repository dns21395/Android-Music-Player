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
    var array = ArrayList<Track>()

    lateinit var callback: Callback

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(array[position])

        holder.itemView.reorder.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_DOWN) onStartDragListener.onStartDrag(holder)
            false
        }
    }

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistViewHolder =
            PlaylistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_moveable_playlist, parent, false))

    fun updateArray(tracks: ArrayList<Track>) {
        array = tracks
        notifyDataSetChanged()
    }

    override fun onItemMove(oldPos: Int, newPos: Int): Boolean {
        callback.onItemMove(oldPos, newPos)
        Collections.swap(array, oldPos, newPos)
        notifyItemMoved(oldPos, newPos)
        return true
    }

    override fun onItemSwipe(pos: Int) {
        callback.onItemSwipe(array[pos].id)
        array.removeAt(pos)
        notifyItemRemoved(pos)
    }

    interface Callback {
        fun onItemMove(oldPos: Int, newPos: Int)
        fun onItemSwipe(trackId: Long)
    }


}