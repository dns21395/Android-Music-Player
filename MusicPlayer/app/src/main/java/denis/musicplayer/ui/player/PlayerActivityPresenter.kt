package denis.musicplayer.ui.player

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 14/01/2018.
 */
class PlayerActivityPresenter<V: PlayerActivityMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val musicManager: MusicManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), PlayerActivityMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        getCurrentTrack()
        getActionPlayPause()
    }

    override fun getCurrentTrack() {
        compositeDisposable.add(
                musicManager.getCurrentTrackBehaviour()
                        .subscribe {
                            getCover(it.albumId)
                            mvpView?.updateTrackInfo(it)
                        }
        )
    }

    override fun getCover(albumID: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.getAlbumImagePath(albumID)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {
                            mvpView?.updateTrackCover(it)
                        }, {mvpView?.updateTrackCover(null)})
        )
    }


    override fun getActionPlayPause() {
        compositeDisposable.add(
                musicManager.actionBehaviour()
                        .subscribe {
                            mvpView?.updatePlayPause(it)
                        }
        )
    }

    override fun callActions(action: MusicManagerAction) {
        when (CommonUtils.isRunning(context, AppMusicService::class.java)) {
            true -> musicManager.makeAction(action)
            false -> AppMusicService.start(context)
        }
    }



}