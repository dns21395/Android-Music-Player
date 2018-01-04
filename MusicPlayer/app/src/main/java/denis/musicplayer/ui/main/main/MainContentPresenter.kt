package denis.musicplayer.ui.main.main

import android.content.Context
import denis.musicplayer.data.DataManager

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
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainContentMvpPresenter<V> {

}