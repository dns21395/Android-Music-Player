package denis.musicplayer.ui.main.base

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by denis on 02/01/2018.
 */
abstract class MainBaseAdapter<A : MainBaseViewHolder<B>,
                               B: Any,
                               C: MainBaseFragmentMvpView<B>,
                               D: MainBaseMvpPresenter<C, B>>
    : RecyclerView.Adapter<A>() {

    private val TAG = "MainBaseAdapter"

    lateinit var presenter: D

    override fun onBindViewHolder(holder: A, position: Int) {
        setViewHolderBackground(holder, position)

        presenter.onBindItemAtPosition(holder, position)

        holder.itemView.setOnLongClickListener {
            when(presenter.getSelectedArrayCount()) {
                0 -> {
                    presenter.addSelectedItem(presenter.getItemAtPosition(position))
                    setViewHolderBackground(holder, position)
                }
                else -> {
                    selectItem(holder, position)
                }
            }
            true
        }

        holder.itemView.setOnClickListener {
            when(presenter.getSelectedArrayCount()) {
                0 -> presenter.onItemClick(position)
                else -> selectItem(holder, position)
            }
        }

    }

    private fun setViewHolderBackground(holder: A, position: Int) {
        when(presenter.getSelectedArrayCount()) {
            0 -> holder.setBackground(position)
            else -> {
                when(presenter.isItemSelected(presenter.getItemAtPosition(position))) {
                    true -> holder.setSelectedBackground(position)
                    false -> holder.setBackground(position)
                }

            }
        }
    }

    private fun selectItem(holder: A, position: Int) {
        val item = presenter.getItemAtPosition(position)
        when(presenter.isItemSelected(item)) {
            true -> {
                presenter.removeSelectedItem(item)
            }
            false -> {
                presenter.addSelectedItem(item)
            }
        }
        setViewHolderBackground(holder, position)
    }

    override fun getItemCount(): Int = presenter.getArrayCount()

    fun updateArray(array: ArrayList<B>) {
        presenter.updateArray(array)
        notifyDataSetChanged()
    }

}