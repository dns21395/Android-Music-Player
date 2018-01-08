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
                        val rxBus: MainRxBus)
    : MainBasePresenter<V>(context, dataManager, compositeDisposable), AlbumMvpPresenter<V> {

    private val TAG = "AlbumPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.addAll(
                rxBus.toObservable().subscribe {
                    if(it == MainEnumRxBus.SHOW_UPDATE_PLAYLIST_DIALOG) mvpView.getAlbumTracks()
                },
                rxBus.toObservable().subscribe {
                    if(it == MainEnumRxBus.CANCEL_SELECTING) mvpView.cancelSelecting()
                }
        )

        getAlbums()
    }

    override fun getAlbums() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanAlbums()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        })
    }

    override fun getAlbumTracks(array: ArrayList<Album>) {
        compositeDisposable.add(
                Observable.fromCallable {
                    val tracks = ArrayList<Track>()

                    array
                            .map { dataManager.scanAlbumTracks(it.id) }
                            .forEach { newTracks ->
                                newTracks
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
}