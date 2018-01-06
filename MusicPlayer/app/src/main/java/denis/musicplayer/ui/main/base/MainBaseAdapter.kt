package denis.musicplayer.ui.main.base

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by denis on 02/01/2018.
 */
abstract class MainBaseAdapter<A : MainBaseViewHolder,
                               B: Any,
                               C: MainBaseAdapter.Callback> : RecyclerView.Adapter<A>() {

    private val TAG = "MainBaseAdapter"

    lateinit var callback: C
    var array = ArrayList<B>()
    val selectedArray = ArrayList<B>()

    override fun onBindViewHolder(holder: A, position: Int) {
        setBackground(holder, position)

        holder.itemView.setOnLongClickListener {
            when(selectedArray.size) {
                0 -> {
                    callback.startSelecting()
                    selectedArray.add(array[position])
                    setBackground(holder, position)
                }
                else -> {
                    selectItem(holder, position)
                }
            }
            true
        }

        holder.itemView.setOnClickListener {
            if(selectedArray.size > 0) selectItem(holder, position)
        }

    }

    fun setBackground(holder: A, position: Int) {
        when(selectedArray.size) {
            0 -> holder.setBackground(position)
            else -> {
                when(selectedArray.contains(array[position])) {
                    true -> holder.setSelectedBackground(position)
                    false -> holder.setBackground(position)
                }

            }
        }
    }

    private fun selectItem(holder: A, position: Int) {
        when(selectedArray.contains(array[position])) {
            true -> {
                selectedArray.remove(array[position])
                Log.d(TAG, "$selectedArray")
                if(selectedArray.size == 0) callback.stopSelecting()
            }
            false -> {
                selectedArray.add(array[position])
            }
        }
        setBackground(holder, position)
    }

    override fun getItemCount(): Int = array.size

    fun updateArray(array: ArrayList<B>) {

        this.array = array
        notifyDataSetChanged()

        for(item in array) {
            Log.d(TAG, "$item")
        }
    }

    interface Callback {
        fun startSelecting()
        fun stopSelecting()
    }
}