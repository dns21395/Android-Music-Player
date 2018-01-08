package denis.musicplayer.ui.main.track

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseFragment
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * Created by denis on 31/12/2017.
 */
class TrackFragment : MainBaseFragment(), TrackMvpView {
    companion object {
        fun newInstance(): TrackFragment {
            val args = Bundle()
            val fragment = TrackFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: TrackMvpPresenter<TrackMvpView>

    @Inject lateinit var layoutManager: LinearLayoutManager

    @Inject lateinit var adapter: TrackAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.presenter = presenter
    }

    override fun updateArray(array: ArrayList<Track>) {
        adapter.updateArray(array)
    }
}