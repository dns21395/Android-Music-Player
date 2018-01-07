package denis.musicplayer.ui.main.updateplaylist

import android.content.Context
import android.os.Bundle
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.utils.BytesUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class UpdatePlaylistPresenter<V: UpdatePlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), UpdatePlaylistMvpPresenter<V> {

    private val TAG = "UpdatePlaylistPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        getPlaylist()
    }

    override fun getPlaylist() {
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

    override fun updatePlaylist(bundle: Bundle, playlistId: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                    val objects = bundle.getByteArray(UpdatePlaylistDialog.KEY_TRACKS)
                    if(objects != null) {
                        val tracks = BytesUtil.toObject<Track>(objects)
                        for(track in tracks) {
                            Log.d(TAG, "$track")
                        }
                        Log.d(TAG, "ID : $playlistId")
                        dataManager.addTracksToPlaylist(playlistId, tracks)
                    }
                }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.finishDialog(R.string.playlist_updated)
                        })

    }

}