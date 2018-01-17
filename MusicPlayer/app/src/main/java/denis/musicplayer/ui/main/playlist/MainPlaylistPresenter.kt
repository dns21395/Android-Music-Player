package denis.musicplayer.ui.main.playlist

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.main.EnumMainManager
import denis.musicplayer.data.main.MainManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class MainPlaylistPresenter<V: MainPlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val mainManager: MainManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), MainPlaylistMvpPresenter<V> {

    private val TAG = "MainPlaylistPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.add(
                mainManager.getAction().subscribe {
                    if(it == EnumMainManager.UPDATE_PLAYLIST) getPlaylists()
                }
        )

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