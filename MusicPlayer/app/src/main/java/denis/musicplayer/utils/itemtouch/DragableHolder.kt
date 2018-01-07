package denis.musicplayer.utils.itemtouch

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import denis.musicplayer.R
import org.jetbrains.anko.backgroundColor

/**
 * Created by denis on 07/01/2018.
 */
abstract class DragableHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {


    fun changeBackground(color: Int) = with(itemView) {
        itemView.backgroundColor = ContextCompat.getColor(context, color)
    }

    override fun onItemSelected() = with(itemView) {
        changeBackground(R.color.item_drag)
    }

    override fun onItemClear() = with(itemView) {
        changeBackground(android.R.color.transparent)
    }
}