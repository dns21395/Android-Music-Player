package denis.musicplayer.ui.base

import android.content.Context
import denis.musicplayer.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
open class BasePresenter<V: MvpView>
    @Inject constructor(val context: Context,
                        val dataManager: DataManager,
                        val compositeDisposable: CompositeDisposable) : MvpPresenter<V> {
    var mvpView: V? = null

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        mvpView = null
    }
}