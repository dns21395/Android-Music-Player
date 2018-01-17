package denis.musicplayer.ui.player.fragment

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.base.MvpView
import denis.musicplayer.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class PlayerFragmentPresenter<V : PlayerFragmentMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val musicManager: MusicManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), PlayerFragmentMvpPresenter<V> {

    private val TAG = "PlayerFragmentPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        updateFragment()
        actionCondition()

        if(musicManager.getTracksSize() == 0) mvpView.updateCover(null)
    }

    override fun updateFragment() {
        compositeDisposable.add(
                musicManager.getCurrentTrackBehaviour()
                        .subscribe {
                            getTrackImage(it.albumId)
                            mvpView?.updateFragment(it)
                        }
        )
    }

    override fun actionCondition() {
        compositeDisposable.add(
                musicManager.actionBehaviour()
                        .subscribe {
                            mvpView?.updateAction(it)
                        }
        )
    }

    override fun callAction() {
        if(musicManager.getTracksSize() > 0) {
            when (CommonUtils.isRunning(context, AppMusicService::class.java)) {
                true -> musicManager.resumePause()
                false -> AppMusicService.start(context)
            }
        }
    }

    private fun getTrackImage(albumID: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                        dataManager.getAlbumImagePath(albumID)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {
                            mvpView?.updateCover(it)
                        }, {mvpView?.updateCover(null)})
        )
    }



}