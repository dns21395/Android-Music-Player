package denis.musicplayer.ui.main.base

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.EnumSelectManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
open class MainBasePresenter<V: MainBaseFragmentMvpView<T>, T: Any>
    @Inject constructor(context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        private val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainBaseMvpPresenter<V, T> {


    private var array = ArrayList<T>()

    private var selectedArray = ArrayList<T>()

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.addAll(
                selectManager.getActions().subscribe {
                    if(getSelectedArrayCount() > 0) {
                        when (it) {
                            EnumSelectManager.CLEAR_ITEMS -> clearSelectedArray()
                            EnumSelectManager.INSERT_ITEMS -> getItemsForPlaylist()
                        }
                    }
                }
        )

        getItems()
    }

    // Important functions that has to be overridden

    override fun getItems() {

    }

    override fun getItemsForPlaylist() {

    }

    override fun onItemClick(position: Int) {

    }

    // Items

    override fun getArray(): ArrayList<T> = array

    override fun getItemAtPosition(position: Int): T = array[position]

    override fun getArrayCount(): Int = array.size

    override fun onBindItemAtPosition(holder: MainBaseViewHolder<T>, position: Int) {
        holder.onBind(array[position])
    }

    override fun updateArray(array: ArrayList<T>) {
        this.array = array
    }

    // Selected Items

    override fun addSelectedItem(item : T) {
        selectedArray.add(item)
        selectManager.updateSelectedItemsSize(getSelectedArrayCount())

    }

    override fun removeSelectedItem(item : T) {
        selectedArray.remove(item)
        selectManager.updateSelectedItemsSize(getSelectedArrayCount())
    }

    override fun clearSelectedArray() {
        selectedArray.clear()
        selectManager.updateSelectedItemsSize(getSelectedArrayCount())
        mvpView?.notifyDataSetChanged()
    }

    override fun getSelectedArray(): ArrayList<T> = selectedArray

    override fun getSelectedArrayCount(): Int = selectedArray.size

    override fun isItemSelected(item : T): Boolean = selectedArray.contains(item)

}