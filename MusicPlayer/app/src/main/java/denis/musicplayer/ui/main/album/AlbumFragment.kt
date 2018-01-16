package denis.musicplayer.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragment
import denis.musicplayer.ui.main.category.CategoryActivity
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class AlbumFragment : MainBaseFragment<AlbumAdapter, AlbumViewHolder, Album, AlbumMvpView, AlbumMvpPresenter<AlbumMvpView>>(), AlbumMvpView {
    companion object {
        fun newInstance(): AlbumFragment {
            val args = Bundle()
            val fragment = AlbumFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: AlbumMvpPresenter<AlbumMvpView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container, false)

    override fun setUp(view: View?) {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.isMotionEventSplittingEnabled = false
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.presenter = presenter
    }


}