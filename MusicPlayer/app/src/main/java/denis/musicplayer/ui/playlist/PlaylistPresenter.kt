package denis.musicplayer.ui.playlist

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

/**
 * Created by denis on 04/01/2018.
 */
class PlaylistPresenter<V: PlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val musicManager: MusicManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), PlaylistMvpPresenter<V> {

    private val TAG = "PlaylistPresenter"

    private var playlistId: Long = 0

    private var array = ArrayList<Track>()

    override fun setPlaylistId(id: Long) {
        playlistId = id
    }

    override fun updateArray(array: ArrayList<Track>) {
        this.array = array
    }

    override fun getTrackByPosition(position: Int): Track = array[position]

    override fun getArraySize(): Int = array.size

    override fun getTracks(id: Long) {
        playlistId = id
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.getPlaylistTracks(id)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        }
        )
    }

    override fun swapArrayItems(oldPos: Int, newPos: Int) {
        Collections.swap(array, oldPos, newPos)
    }

    override fun onItemClick(position: Int) {
        compositeDisposable.add(Observable.fromCallable {
            musicManager.updateTracks(array, position)
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

    override fun removeItemAt(position: Int) {
        array.removeAt(position)
    }

    override fun deletePlaylist(id: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.deletePlaylist(id)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.onPlaylistDeleted()
                        }
        )
    }

    override fun onItemSwipe(trackId: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.deletePlaylistTrack(playlistId, trackId)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
    }

    override fun onItemMove(oldPos: Int, newPos: Int) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.playlistItemReorder(playlistId, oldPos, newPos)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
    }
}