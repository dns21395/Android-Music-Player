package denis.musicplayer.ui.main.base

import android.support.v7.widget.LinearLayoutManager
import denis.musicplayer.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
abstract class MainBaseActivity<A : MainBaseAdapter<B, C, D, E>,
                                B: MainBaseViewHolder<C>,
                                C: Any,
                                D: MainBaseMvpView<C>,
                                E: MainBaseMvpPresenter<D, C>>
    : BaseActivity(), MainBaseMvpView<C> {

    @Inject lateinit var adapter: A

    @Inject lateinit var layoutManager: LinearLayoutManager
}