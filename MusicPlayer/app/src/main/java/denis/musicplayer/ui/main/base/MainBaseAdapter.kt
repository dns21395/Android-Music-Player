package denis.musicplayer.ui.main.base

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by denis on 02/01/2018.
 */
abstract class MainBaseAdapter<A : MainBaseViewHolder, B: Any> : RecyclerView.Adapter<A>() {

    private val TAG = "MainBaseAdapter"

    var array = ArrayList<B>()

    override fun onBindViewHolder(holder: A, position: Int) {
        holder.setBackground(position)
    }

    override fun getItemCount(): Int = array.size

    fun updateArray(array: ArrayList<B>) {

        this.array = array
        notifyDataSetChanged()

        for(item in array) {
            Log.d(TAG, "$item")
        }
    }
}