package denis.musicplayer.ui.main

import android.content.Context
import denis.musicplayer.data.DataManager
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
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainMvpPresenter<V>{
}