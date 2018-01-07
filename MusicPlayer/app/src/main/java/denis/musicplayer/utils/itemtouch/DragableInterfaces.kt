package denis.musicplayer.utils.itemtouch

import android.support.v7.widget.RecyclerView

/**
 * Created by denis on 07/01/2018.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(oldPos: Int, newPos: Int): Boolean
    fun onItemSwipe(pos: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}