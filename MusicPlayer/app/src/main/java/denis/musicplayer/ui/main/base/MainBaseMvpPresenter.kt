package denis.musicplayer.ui.main.base

import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 31/12/2017.
 */
interface MainBaseMvpPresenter<V : MainBaseFragmentMvpView<T>, T: Any> : MvpPresenter<V> {

    fun getItems()

    fun getItemsForPlaylist()

    fun onItemClick(position: Int)

    // Items

    fun getArray(): ArrayList<T>

    fun getItemAtPosition(position: Int): T

    fun getArrayCount(): Int

    fun onBindItemAtPosition(holder: MainBaseViewHolder<T>, position: Int)

    fun updateArray(array: ArrayList<T>)

    // Selected Items

    fun addSelectedItem(item : T)

    fun removeSelectedItem(item : T)

    fun clearSelectedArray()

    fun getSelectedArray(): ArrayList<T>

    fun getSelectedArrayCount(): Int

    fun isItemSelected(item : T): Boolean
}