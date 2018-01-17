package denis.musicplayer.ui.player.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.utils.BytesUtil
import denis.musicplayer.utils.ImageTransformToCircle
import kotlinx.android.synthetic.main.fragment_player.*
import java.io.File
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class PlayerFragment : BaseFragment(), PlayerFragmentMvpView {

    private val TAG = "PlayerFragment"

    companion object {
        fun newInstance(): PlayerFragment {
            val args = Bundle()
            val fragment = PlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject lateinit var presenter: PlayerFragmentMvpPresenter<PlayerFragmentMvpView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_player, container, false)

    override fun setUp(view: View?) {
        playPause.setOnClickListener {
            presenter.callAction()
        }
    }

    override fun updateFragment(track: Track) {
        if(title != null) {
            title.text = track.title
            artist.text = track.artist
        }
    }

    override fun updateCover(coverPath: String?) {
        if(cover != null) {
            when (coverPath) {
                null -> {
                    Picasso.with(context)
                            .load(Uri.parse("android.resource://gabyshev.denis.musicplayer/drawable/no_music"))
                            .transform(ImageTransformToCircle())
                            .resize(96, 96)
                            .centerCrop()
                            .into(cover)
                }
                else -> {
                    Picasso.with(context)
                            .load(Uri.fromFile(File(coverPath)))
                            .transform(ImageTransformToCircle())
                            .resize(96, 96)
                            .centerCrop()
                            .into(cover)
                }
            }
        }
    }


    override fun updateAction(isMusicPlaying: Boolean) {
        if(playPause != null) {
            when(isMusicPlaying) {
                true -> playPause.setImageResource(R.drawable.pause)
                false -> playPause.setImageResource(R.drawable.play)
            }
        }
    }
}