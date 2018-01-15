package denis.musicplayer.ui.main.genre

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.data.media.model.Genre
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
class GenrePresenter<V: GenreMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        selectManager: SelectManager)
    : MainBasePresenter<V, Genre>(context, dataManager, compositeDisposable, selectManager), GenreMvpPresenter<V> {

    private val TAG = "GenrePresenter"

    override fun getItems() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanGenres()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        }
        )
    }

    override fun onItemClick(position: Int) {
        val genre = getItemAtPosition(position)
        getGenreTracks(genre.id, genre.name)
    }



    override fun getItemsForPlaylist() {
        compositeDisposable.add(
                Observable.fromCallable {
                    val tracks = ArrayList<Track>()
                    val array = getSelectedArray()

                    array
                            .map { dataManager.scanGenreTracks(it.id) }
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

    override fun getGenreTracks(genreID: Long, title: String) {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanGenreTracks(genreID)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.openCategoryTracksActivity(it, title)
                        }
        )
    }


}