package denis.musicplayer.ui.main.base

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
open class MainBasePresenter<V: MainBaseMvpView>
    @Inject constructor(context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainBaseMvpPresenter<V> {
}