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
import denis.musicplayer.ui.playlist.PlaylistActivity
import kotlinx.android.synthetic.main.fragment_playlist.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class MainPlaylistFragment : MainBaseFragment(), MainPlaylistMvpView, MainPlaylistAdapter.Callback {
    companion object {
        fun newInstance(): MainPlaylistFragment {
            val args = Bundle()
            val fragment = MainPlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenterMain: MainPlaylistMvpPresenter<MainPlaylistMvpView>

    @Inject lateinit var layoutManager: LinearLayoutManager

    @Inject lateinit var adapter: MainPlaylistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        activityComponent?.inject(this)
        presenterMain.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.callback = this

        fab.setOnClickListener {
            AddPlaylistDialog.newInstance().show(activity?.supportFragmentManager, AddPlaylistDialog.TAG)
        }
    }



    override fun updateArray(array: ArrayList<Playlist>) {
        adapter.updateArray(array)
    }

    override fun onPlaylistClicked(id: Int, title: String) {
        val intent = PlaylistActivity.getStartIntent(context!!)

        val bundle = Bundle()
        bundle.putInt(PlaylistActivity.KEY_ID, id)
        bundle.putString(PlaylistActivity.TITLE_ID, title)

        intent.putExtras(bundle)

        startActivity(intent)
    }
}