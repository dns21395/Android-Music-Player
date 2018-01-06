package denis.musicplayer.ui.playlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_playlist.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by denis on 04/01/2018.
 */
class PlaylistActivity : BaseActivity(), PlaylistMvpView {

    companion object {
        val KEY_ID = "key_id"
        val TITLE_ID = "title_id"

        fun getStartIntent(context: Context): Intent = Intent(context, PlaylistActivity::class.java)
    }

    @Inject lateinit var presenter: PlaylistMvpPresenter<PlaylistMvpView>

    var id = 0
    var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        id = intent.extras?.getInt(KEY_ID) ?: 0
        title = intent.extras?.getString(TITLE_ID) ?: ""

        playlistTitle.text = title

        delete.setOnClickListener {
            presenter.deletePlaylist(id)
        }
    }

    override fun onPlaylistDeleted() {
        toast(getString(R.string.playlist_deleted, title))
        finish()
    }
}