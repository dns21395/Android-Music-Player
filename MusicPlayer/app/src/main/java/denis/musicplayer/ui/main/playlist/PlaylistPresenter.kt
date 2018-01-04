package denis.musicplayer.ui.main.playlist

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.MainMvpView
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class PlaylistPresenter<V: PlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : MainBasePresenter<V>(context, dataManager, compositeDisposable), PlaylistMvpPresenter<V> {

    private val TAG = "PlaylistPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        getPlaylists()
    }

    override fun getPlaylists() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanPlaylist()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        }
        )
    }
}