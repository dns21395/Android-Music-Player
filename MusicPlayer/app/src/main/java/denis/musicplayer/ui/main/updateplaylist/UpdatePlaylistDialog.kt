package denis.musicplayer.ui.main.updateplaylist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.BaseDialog
import denis.musicplayer.utils.BytesUtil
import kotlinx.android.synthetic.main.dialog_update_playlist.*
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_update_playlist, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val objects = arguments?.getByteArray(KEY_TRACKS)
        if(objects != null) {
            val tracks = BytesUtil.toObject<Track>(objects)

            for(track in tracks) {
                Log.d(TAG, "$track")
            }
        }
    }
}