package denis.musicplayer.ui.main.base

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by denis on 02/01/2018.
 */
abstract class MainBaseAdapter<A : MainBaseViewHolder, B: Any> : RecyclerView.Adapter<A>() {

    private val TAG = "MainBaseAdapter"

    var array = ArrayList<B>()
    val selectedArray = ArrayList<B>()

    override fun onBindViewHolder(holder: A, position: Int) {
        setBackground(holder, position)

        holder.itemView.setOnLongClickListener {
            selectedArray.add(array[position])
            setBackground(holder, position)
            true
        }

        holder.itemView.setOnClickListener {
            if(selectedArray.contains(array[position])) selectedArray.remove(array[position])
            else if(selectedArray.size > 0) selectedArray.add(array[position])
            setBackground(holder, position)
        }

    }

    private fun setBackground(holder: A, position: Int) {
        when(selectedArray.size) {
            0 -> holder.setBackground(position)
            else -> {
                when(selectedArray.contains(array[position])){
                    true -> holder.setSelectedBackground(position)
                    false -> holder.setBackground(position)
                }

            }
        }
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