package denis.musicplayer.ui.main.addplaylist

import android.content.Context
import denis.musicplayer.R
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.main.EnumMainManager
import denis.musicplayer.data.main.MainManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by denis on 03/01/2018.
 */
class AddPlaylistPresenter<V: AddPlaylistMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val mainManager: MainManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), AddPlaylistMvpPresenter<V> {
    override fun createPlaylist(name: String) {
        compositeDisposable.add(
                Observable.fromCallable { dataManager.createPlaylist(name) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            mainManager.callAction(EnumMainManager.UPDATE_PLAYLIST)
                            mvpView?.dismissDialog(AddPlaylistDialog.TAG)
                            context.toast(context.getString(R.string.playlist_created, name))
                        })
    }
}