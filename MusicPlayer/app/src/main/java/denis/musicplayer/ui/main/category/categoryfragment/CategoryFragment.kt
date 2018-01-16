package denis.musicplayer.ui.main.category.categoryfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragment
import denis.musicplayer.ui.player.PlayerActivity
import denis.musicplayer.utils.BytesUtil
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryFragment : MainBaseFragment<CategoryFragmentAdapter,
                                          CategoryFragmentViewHolder,
                                          Track,
                                          CategoryFragmentMvpView,
                                          CategoryFragmentMvpPresenter<CategoryFragmentMvpView>>(), CategoryFragmentMvpView {

    companion object {
        val KEY_TRACKS = "key_tracks"

        fun newInstance(tracks: ArrayList<Track>): CategoryFragment {
            val args = Bundle()
            args.putByteArray(KEY_TRACKS, BytesUtil.toByteArray(tracks))
            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: CategoryFragmentMvpPresenter<CategoryFragmentMvpView>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        recyclerView.isMotionEventSplittingEnabled = false
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.presenter = presenter

        getTracks()
    }

    private fun getTracks() {
        val objects = arguments?.getByteArray(CategoryFragment.KEY_TRACKS)
        if(objects != null) {
            val tracks = BytesUtil.toObjectArray<Track>(objects)
            presenter.updateArray(tracks)
            presenter.getItems()

        }
    }

    override fun openPlayerActivity() {
        if(context != null) {
            startActivity(PlayerActivity.getStartIntent(context!!))
            activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
        }
    }
}