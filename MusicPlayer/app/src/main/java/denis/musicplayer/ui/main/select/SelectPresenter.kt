package denis.musicplayer.ui.main.select

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.EnumSelectManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.main.base.MainEnumRxBus
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class SelectPresenter<V: SelectMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), SelectMvpPresenter<V> {

    private val TAG = "SelectPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.add(
                selectManager.getSelectedItemsSize().subscribe {
                    Log.d(TAG, "count : $it")
                    mvpView.updateCount(it)
                }
        )
    }

    override fun sendMessageToUpdatePlaylist() {
        selectManager.callAction(EnumSelectManager.INSERT_ITEMS)
    }

    override fun cancelSelecting() {
        selectManager.callAction(EnumSelectManager.CLEAR_ITEMS)
    }
}