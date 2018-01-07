package denis.musicplayer.ui.main.addplaylist

import android.app.Dialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_add_playlist.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Created by denis on 03/01/2018.
 */
class AddPlaylistDialog : BaseDialog(), AddPlaylistMvpView {
    companion object {
        val TAG = "AddPlaylistDialog"

        fun newInstance(): AddPlaylistDialog {
            val args = Bundle()
            val fragment = AddPlaylistDialog()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: AddPlaylistMvpPresenter<AddPlaylistMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_add_playlist, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_add_playlist)

        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        return dialog
    }

    override fun setUp(view: View?) {
        positiveButton.setOnClickListener {
            when(playlistName.text.isNotEmpty()) {
                true -> presenter.createPlaylist("${playlistName.text.trim()}")
                false -> toast(R.string.playlist_name_is_empty)
            }
        }

        negativeButton.setOnClickListener {
            dismiss()
        }
    }



}