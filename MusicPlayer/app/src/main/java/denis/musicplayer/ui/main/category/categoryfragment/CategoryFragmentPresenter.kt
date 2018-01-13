package denis.musicplayer.ui.main.category.categoryfragment

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.ui.main.base.MainBasePresenter
import denis.musicplayer.ui.main.base.MainRxBus
import denis.musicplayer.ui.main.category.CategoryMvpPresenter
import denis.musicplayer.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryFragmentPresenter<V: CategoryFragmentMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        rxBus: MainRxBus,
                        val musicManager: MusicManager)
    : MainBasePresenter<V, Track>(context, dataManager, compositeDisposable, rxBus), CategoryFragmentMvpPresenter<V> {

    private val TAG = "CategoryFragmePresenter"

    override fun onItemClick(position: Int) {
        compositeDisposable.add(Observable.fromCallable {
            musicManager.updateTracks(getArray(), position)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when(CommonUtils.isRunning(context, AppMusicService::class.java)) {
                        true -> musicManager.playTrack()
                        false -> AppMusicService.start(context)
                    }
                })
    }

    override fun getItems() {
        if(getArrayCount() > 0) mvpView?.updateArray(getArray())
    }

    override fun getItemsForPlaylist() {
        mvpView?.showUpdatePlaylist(getSelectedArray())
    }
}