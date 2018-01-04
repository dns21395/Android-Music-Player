package denis.musicplayer.ui.splash

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class SplashPresenter<V : SplashMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), SplashMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
    }

}