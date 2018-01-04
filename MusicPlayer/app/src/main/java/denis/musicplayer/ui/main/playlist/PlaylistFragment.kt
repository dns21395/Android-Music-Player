package denis.musicplayer.ui.main.playlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.main.add_playlist.AddPlaylistDialog
import denis.musicplayer.ui.main.base.MainBaseFragment
import kotlinx.android.synthetic.main.fragment_playlist.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class PlaylistFragment : MainBaseFragment(), PlaylistMvpView {
    companion object {
        fun newInstance(): PlaylistFragment {
            val args = Bundle()
            val fragment = PlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: PlaylistMvpPresenter<PlaylistMvpView>

    @Inject lateinit var layoutManager: LinearLayoutManager

    @Inject lateinit var adapter: PlaylistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            AddPlaylistDialog.newInstance().show(activity?.supportFragmentManager, AddPlaylistDialog.TAG)
        }
    }

    override fun updateArray(array: ArrayList<Playlist>) {
        adapter.updateArray(array)
    }

}