package denis.musicplayer.ui.main.updateplaylist

import android.content.Context
import android.os.Bundle
import denis.musicplayer.R
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.data.select.EnumSelectManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.main.base.MainEnumRxBus
import denis.musicplayer.ui.main.base.MainRxBus
import denis.musicplayer.utils.BytesUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class UpdatePlaylistPresenter<V: UpdatePlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), UpdatePlaylistMvpPresenter<V> {

    private val TAG = "UpdatePlaylistPresenter"

    private lateinit var tracks: ArrayList<Track>

    private var array = ArrayList<Playlist>()

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        getPlaylist()
    }

    override fun getPlaylistByPosition(position: Int): Playlist = array[position]

    override fun getArraySize(): Int = array.size

    override fun updateArray(array: ArrayList<Playlist>) {
        this.array = array
    }

    override fun setArrayTracks(bundle: Bundle) {
        Observable.fromCallable {
            val objects = bundle.getByteArray(UpdatePlaylistDialog.KEY_TRACKS)
            if(objects != null) {
                val tracks = BytesUtil.toObjectArray<Track>(objects)
                this.tracks = tracks
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun getPlaylist() {
        compositeDisposable.add(
                Observable.fromCallable {
                    dataManager.scanPlaylist()
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.updateArray(it)
                        }
        )
    }

    override fun updatePlaylist(position: Int) {
        val playlist = array[position]
        compositeDisposable.add(
                Observable.fromCallable {

                    dataManager.addTracksToPlaylist(playlist.id, tracks)
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mvpView?.finishDialog(context.getString(R.string.playlist_updated, playlist.name))
                            selectManager.callAction(EnumSelectManager.CLEAR_ITEMS)
                        })

    }

}