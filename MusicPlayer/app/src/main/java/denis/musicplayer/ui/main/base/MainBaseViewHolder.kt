package denis.musicplayer.ui.main.base

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import denis.musicplayer.R

/**
 * Created by denis on 02/01/2018.
 */
abstract class MainBaseViewHolder<T: Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T)

    fun setBackground(position: Int) = with(itemView) {
        when(position % 2) {
            0 -> itemView.background = ContextCompat.getDrawable(itemView.context, R.color.item_even)
            else -> itemView.background = ContextCompat.getDrawable(itemView.context, R.color.item_odd)
        }
    }

    fun setSelectedBackground(position: Int) = with(itemView) {
        when(position % 2) {
            0 -> itemView.background = ContextCompat.getDrawable(itemView.context, R.color.item_selected_even)
            else -> itemView.background = ContextCompat.getDrawable(itemView.context, R.color.item_selected_odd)
        }
    }
}