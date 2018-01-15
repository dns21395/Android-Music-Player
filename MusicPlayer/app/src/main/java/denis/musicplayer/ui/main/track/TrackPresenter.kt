package denis.musicplayer.ui.main.track

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import denis.musicplayer.ui.player.PlayerActivity
import denis.musicplayer.utils.CommonUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
class TrackPresenter<V : TrackMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        selectManager: SelectManager,
                        val musicManager: MusicManager)
    : MainBasePresenter<V, Track>(context, dataManager, compositeDisposable, selectManager), TrackMvpPresenter<V> {

    private val TAG = "TrackPresenter"

    override fun onItemClick(position: Int) {
        compositeDisposable.add(Observable.fromCallable {
            musicManager.updateTracks(getArray(), position)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mvpView?.openPlayerActivity()
                    when(CommonUtils.isRunning(context, AppMusicService::class.java)) {
                        true -> musicManager.playTrack()
                        false -> AppMusicService.start(context)
                    }
                })
    }

    override fun getItems() {
        compositeDisposable.add(Observable.fromCallable {
            dataManager.scanTracks()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mvpView?.updateArray(it)
                })
    }

    override fun getItemsForPlaylist() {
        mvpView?.showUpdatePlaylist(getSelectedArray())
    }

}