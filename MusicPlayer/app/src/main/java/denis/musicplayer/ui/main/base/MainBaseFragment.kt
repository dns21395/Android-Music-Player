package denis.musicplayer.ui.main.base

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.ui.main.MainMvpPresenter
import denis.musicplayer.ui.main.MainMvpView
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
abstract class MainBaseFragment<A : MainBaseAdapter<B, C, D, E>,
                                B: MainBaseViewHolder<C>,
                                C: Any,
                                D: MainBaseMvpView<C>,
                                E: MainBaseMvpPresenter<D, C>>
    : BaseFragment(), MainBaseMvpView<C> {

    private val TAG = "MainBaseFragment"

    @Inject lateinit var adapter: A

    @Inject lateinit var layoutManager: LinearLayoutManager

    override fun showSelectFragment() {
        (activity as MainActivity).replaceFragment((activity as MainActivity).selectFragment)
    }

    override fun hideSelectFragment() {
        adapter.notifyDataSetChanged()
        (activity as MainActivity).replaceFragment((activity as MainActivity).playerFragment)
    }

    override fun updateCountSelectFragment(count: Int) {
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.frameLayout) as SelectFragment?

        fragment?.updateCount(count)
    }

    override fun showUpdatePlaylist(array: ArrayList<Track>) {
        UpdatePlaylistDialog.newInstance(array).show(activity?.supportFragmentManager, UpdatePlaylistDialog.TAG)
    }

    override fun updateArray(array: ArrayList<C>) {
        adapter.updateArray(array)
    }
}