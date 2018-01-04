package denis.musicplayer.ui.main.playlist

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.base.MainBasePresenter
import denis.musicplayer.ui.main.base.MainRxBus
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import denis.musicplayer.ui.main.base.MainEnumRxBus.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class PlaylistPresenter<V: PlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val rxBus: MainRxBus)
    : MainBasePresenter<V>(context, dataManager, compositeDisposable), PlaylistMvpPresenter<V> {

    private val TAG = "PlaylistPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.add(
                rxBus.toObservable()
                        .subscribe {
                            Log.d(TAG, "$it")

                            if(it == UPDATE_PLAYLIST) getPlaylists()


                        }
        )

        getPlaylists()
    }

    override fun getPlaylists() {
        compositeDisposable.add(
                Flowable.fromCallable {
                    dataManager.scanPlaylist()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d(TAG, "update array")
                            mvpView?.updateArray(it)
                        }
        )
    }
}