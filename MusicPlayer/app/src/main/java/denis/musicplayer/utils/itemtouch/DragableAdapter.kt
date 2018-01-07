package denis.musicplayer.utils.itemtouch

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import denis.musicplayer.R

/**
 * Created by denis on 07/01/2018.
 */

abstract class DragableAdapter<T: DragableHolder>
    : RecyclerView.Adapter<T>(), ItemTouchHelperAdapter, OnStartDragListener {

    lateinit var itemTouchHelperCallback: ItemTouchHelperCallback<T>
    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var onStartDragListener: OnStartDragListener

    // ADAPTER NOT WORKING WITHOUT THIS FUNCTION
    fun setItemTouchHelper(recyclerView: RecyclerView) {
        itemTouchHelperCallback = ItemTouchHelperCallback(this)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        onStartDragListener = this
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}