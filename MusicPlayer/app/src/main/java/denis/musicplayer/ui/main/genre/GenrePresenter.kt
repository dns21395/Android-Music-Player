package denis.musicplayer.ui.main.genre

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class GenrePresenter<V: GenreMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), GenreMvpPresenter<V> {

    private val TAG = "GenrePresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        getGenres()
    }

    override fun getGenres() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanGenres()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            for(item in it) {
                                Log.d(TAG, "$item")
                            }
                        }
        )
    }

}