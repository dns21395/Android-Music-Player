package denis.musicplayer.ui.main.artist

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.data.media.model.Artist
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.base.MainBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class ArtistPresenter<V : ArtistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        selectManager: SelectManager)
    : MainBasePresenter<V, Artist>(context, dataManager, compositeDisposable, selectManager), ArtistMvpPresenter<V> {

    private val TAG = "ArtistPresenter"

    override fun getItems() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanArtists()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        }
        )
    }

    override fun getItemsForPlaylist() {
        compositeDisposable.add(
                Observable.fromCallable {
                    val tracks = ArrayList<Track>()
                    val array = getSelectedArray()

                    array
                            .map { dataManager.scanArtistTracks(it.id) }
                            .forEach { artistTracks ->
                                artistTracks
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

    override fun onItemClick(position: Int) {
        val artist = getItemAtPosition(position)
        getArtistTracks(artist.id, artist.artist)
    }

    override fun getArtistTracks(artistId: Long, artistName: String) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanArtistTracks(artistId)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.openCategoryTracksActivity(it, artistName)
                        }
        )
    }

}