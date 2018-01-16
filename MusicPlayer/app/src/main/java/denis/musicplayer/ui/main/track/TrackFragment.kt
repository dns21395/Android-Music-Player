package denis.musicplayer.ui.main.track

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragment
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import denis.musicplayer.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.support.v4.act
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
class TrackFragment : MainBaseFragment<TrackAdapter, TrackViewHolder, Track, TrackMvpView, TrackMvpPresenter<TrackMvpView>>(), TrackMvpView {
    companion object {
        fun newInstance(): TrackFragment {
            val args = Bundle()
            val fragment = TrackFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: TrackMvpPresenter<TrackMvpView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container, false)

    override fun setUp(view: View?) {
        recyclerView.isMotionEventSplittingEnabled = false
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.presenter = presenter
    }

    override fun openPlayerActivity() {
        if(context != null) {
            startActivity(PlayerActivity.getStartIntent(context!!))
            activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
        }
    }
}