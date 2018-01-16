package denis.musicplayer.ui.main.playlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.addplaylist.AddPlaylistDialog
import denis.musicplayer.ui.playlist.PlaylistActivity
import kotlinx.android.synthetic.main.fragment_playlist.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class MainPlaylistFragment : BaseFragment(), MainPlaylistMvpView, MainPlaylistAdapter.Callback {
    companion object {
        fun newInstance(): MainPlaylistFragment {
            val args = Bundle()
            val fragment = MainPlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val TAG = "MainPlaylistFragment"

    @Inject lateinit var presenter: MainPlaylistMvpPresenter<MainPlaylistMvpView>

    @Inject lateinit var layoutManager: LinearLayoutManager

    @Inject lateinit var adapter: MainPlaylistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_playlist, container, false)

    override fun setUp(view: View?) {
        recyclerView.isMotionEventSplittingEnabled = false
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

    override fun onPlaylistClicked(id: Long, title: String) {
        val intent = PlaylistActivity.getStartIntent(context!!)

        val bundle = Bundle()
        bundle.putLong(PlaylistActivity.KEY_ID, id)
        bundle.putString(PlaylistActivity.KEY_TITLE, title)

        intent.putExtras(bundle)

        startActivityForResult(intent, PlaylistActivity.CODE_PLAYLIST_DELETED)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PlaylistActivity.CODE_PLAYLIST_DELETED &&
                resultCode == PlaylistActivity.CODE_PLAYLIST_DELETED) {
            if(data?.getBooleanExtra(PlaylistActivity.KEY_DELETED, false) == true) {
                presenter.getPlaylists()
            }
        }
    }
}

