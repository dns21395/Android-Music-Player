package denis.musicplayer.ui.main.album

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
class AlbumPresenter<V : AlbumMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : MainBasePresenter<V>(context, dataManager, compositeDisposable), AlbumMvpPresenter<V> {

    private val TAG = "AlbumPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        getAlbums()
    }

    override fun getAlbums() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanAlbums()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        })
    }
}