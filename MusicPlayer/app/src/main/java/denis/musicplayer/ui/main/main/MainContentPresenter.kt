package denis.musicplayer.ui.main.main

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.EnumSelectManager
import denis.musicplayer.data.select.SelectManager

import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class MainContentPresenter<V : MainContentMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainContentMvpPresenter<V> {

    override fun callActionClear() {
        selectManager.callAction(EnumSelectManager.CLEAR_ITEMS)
    }

}