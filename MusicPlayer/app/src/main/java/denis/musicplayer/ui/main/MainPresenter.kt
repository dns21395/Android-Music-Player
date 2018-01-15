package denis.musicplayer.ui.main

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class MainPresenter<V: MainMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainMvpPresenter<V> {

    private val TAG = "MainPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.addAll(
                selectManager.getSelectedItemsSize().subscribe {
                    when(it) {
                        0 -> mvpView.hideSelectFragment()
                        else -> mvpView.showSelectFragment()
                    }
                }
        )
    }
}