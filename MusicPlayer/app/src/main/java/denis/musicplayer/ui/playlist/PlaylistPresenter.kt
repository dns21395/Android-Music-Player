package denis.musicplayer.ui.playlist

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
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
                        compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(context, dataManager, compositeDisposable), PlaylistMvpPresenter<V> {

    private val TAG = "PlaylistPresenter"

    private var array = ArrayList<Track>()

    override fun updateArray(array: ArrayList<Track>) {
        this.array = array
    }

    override fun getTrackByPosition(position: Int): Track = array[position]

    override fun getArraySize(): Int = array.size

    override fun getTracks(id: Long) {
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

    override fun onItemSwipe(playlistId: Long, trackId: Long) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.deletePlaylistTrack(playlistId, trackId)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
    }

    override fun onItemMove(playlistId: Long, oldPos: Int, newPos: Int) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.playlistItemReorder(playlistId, oldPos, newPos)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
    }
}