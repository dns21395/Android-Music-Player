package denis.musicplayer.ui.main.base

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
open class MainBasePresenter<V: MainBaseFragmentMvpView<T>, T: Any>
    @Inject constructor(context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val rxBus: MainRxBus)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainBaseMvpPresenter<V, T> {


    override fun getItems() {

    }

    override fun getItemsForPlaylist() {

    }

    override fun onItemClick(position: Int) {

    }

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.addAll(
                rxBus.toObservable().subscribe {
                    if(getSelectedArrayCount() > 0 && it == MainEnumRxBus.SHOW_UPDATE_PLAYLIST_DIALOG) getItemsForPlaylist()
                },
                rxBus.toObservable().subscribe {
                    if(getSelectedArrayCount() > 0 && it == MainEnumRxBus.CANCEL_SELECTING) clearSelectedArray()
                }
        )

        getItems()
    }

    private var array = ArrayList<T>()

    private var selectedArray = ArrayList<T>()

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
        when(getSelectedArrayCount()) {
            0 -> {
                mvpView?.showSelectFragment()
                selectedArray.add(item)
            }
            else -> {
                selectedArray.add(item)
                mvpView?.updateCountSelectFragment(getSelectedArrayCount())
            }
        }
    }

    override fun removeSelectedItem(item : T) {
        selectedArray.remove(item)

        if(getSelectedArrayCount() == 0) mvpView?.hideSelectFragment()
    }

    override fun clearSelectedArray() {
        selectedArray.clear()
        mvpView?.hideSelectFragment()
    }

    override fun getSelectedArray(): ArrayList<T> = selectedArray

    override fun getSelectedArrayCount(): Int = selectedArray.size

    override fun isItemSelected(item : T): Boolean = selectedArray.contains(item)

}