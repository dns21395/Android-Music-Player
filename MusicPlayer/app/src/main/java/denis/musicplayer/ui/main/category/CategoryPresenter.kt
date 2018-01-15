package denis.musicplayer.ui.main.category

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.select.SelectManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryPresenter<V: CategoryMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val selectManager: SelectManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), CategoryMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.addAll(
                selectManager.getSelectedItemsSize().subscribe {
                    when(it) {
                        0 -> mvpView.hideSelectFragment()
                        else -> mvpView.showSelectFragment()
                    }
                }
        )
    }
}