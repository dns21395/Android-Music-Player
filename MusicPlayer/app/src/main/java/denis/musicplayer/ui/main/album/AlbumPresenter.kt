package denis.musicplayer.ui.main.album

import android.content.Context
import android.util.Log
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.base.MainBasePresenter
import denis.musicplayer.ui.main.base.MainEnumRxBus
import denis.musicplayer.ui.main.base.MainRxBus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class AlbumPresenter<V : AlbumMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        rxBus: MainRxBus)
    : MainBasePresenter<V, Album>(context, dataManager, compositeDisposable, rxBus), AlbumMvpPresenter<V> {

    private val TAG = "AlbumPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
    }

    override fun getItems() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanAlbums()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        })
    }



    override fun getItemsForPlaylist() {
        compositeDisposable.add(
                Observable.fromCallable {
                    val tracks = ArrayList<Track>()
                    val array = getSelectedArray()

                    array
                            .map { dataManager.scanAlbumTracks(it.id) }
                            .forEach { albumTracks ->
                                albumTracks
                                        .filterNot { tracks.contains(it) }
                                        .forEach { tracks.add(it) }
                            }

                    tracks
                }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                mvpView?.showUpdatePlaylist(it)
                            }

        )
    }

    override fun getAlbumTracks(albumId: Long, title: String) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanAlbumTracks(albumId)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.openAlbumTracksActivity(it, title)
                        }
        )
    }
}