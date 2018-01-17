package denis.musicplayer.ui.main.updateplaylist

import android.app.Dialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.ui.base.BaseDialog
import denis.musicplayer.utils.BytesUtil
import kotlinx.android.synthetic.main.dialog_update_playlist.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class UpdatePlaylistDialog : BaseDialog(), UpdatePlaylistMvpView {

    companion object {
        val TAG = "UpdatePlaylistDialog"
        val KEY_TRACKS = "key_tracks"

        fun newInstance(tracks: ArrayList<Track>): UpdatePlaylistDialog {
            val args = Bundle()
            args.putByteArray(KEY_TRACKS, BytesUtil.toByteArray(tracks))
            val fragment = UpdatePlaylistDialog()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: UpdatePlaylistMvpPresenter<UpdatePlaylistMvpView>

    @Inject lateinit var adapter: UpdatePlaylistAdapter

    @Inject lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(arguments) {
            null -> finishDialog(getString(R.string.unknown_error))
            else -> presenter.setArrayTracks(arguments!!)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_update_playlist, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)


        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_update_playlist)

        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)

        return dialog
    }

    override fun setUp(view: View?) {
        recyclerView.isMotionEventSplittingEnabled = false
        recyclerView.layoutManager = layoutManager
        adapter.presenter = presenter
        recyclerView.adapter = adapter
    }

    override fun updateArray(array: ArrayList<Playlist>) {
        adapter.updateArray(array)
    }

    override fun finishDialog(toastText: String) {
        toast(toastText)
        dismissDialog(TAG)
    }
}