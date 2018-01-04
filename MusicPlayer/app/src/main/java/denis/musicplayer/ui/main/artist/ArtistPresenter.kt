package denis.musicplayer.ui.main.artist

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class ArtistPresenter<V : ArtistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : MainBasePresenter<V>(context, dataManager, compositeDisposable), ArtistMvpPresenter<V> {

    private val TAG = "ArtistPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        getArtists()
    }

    override fun getArtists() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanArtists()
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