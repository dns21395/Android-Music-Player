package denis.musicplayer.ui.main.category.categoryfragment

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.base.MainBasePresenter
import denis.musicplayer.ui.main.base.MainRxBus
import denis.musicplayer.ui.main.category.CategoryMvpPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryFragmentPresenter<V: CategoryFragmentMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        rxBus: MainRxBus)
    : MainBasePresenter<V, Track>(context, dataManager, compositeDisposable, rxBus), CategoryFragmentMvpPresenter<V> {


    override fun getItems() {
        mvpView?.updateArray(getArray())
    }

    override fun getItemsForPlaylist() {
        mvpView?.showUpdatePlaylist(getSelectedArray())
    }
}