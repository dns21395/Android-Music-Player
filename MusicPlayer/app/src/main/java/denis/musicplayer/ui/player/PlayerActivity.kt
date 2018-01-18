package denis.musicplayer.ui.player

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.squareup.picasso.Picasso
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.BaseActivity
import denis.musicplayer.utils.ImageTransformToCircle
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_player.*
import java.io.File
import javax.inject.Inject

/**
 * Created by denis on 14/01/2018.
 */
class PlayerActivity : BaseActivity(), PlayerActivityMvpView {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, PlayerActivity::class.java)
    }

    @Inject lateinit var presenter: PlayerActivityMvpPresenter<PlayerActivityMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        activityComponent.inject(this)
        presenter.onAttach(this)
        setUp()
    }

    override fun setUp() {
        transparentStatusBar()
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
        playPause.setOnClickListener { presenter.callActions(MusicManagerAction.RESUMEPAUSE) }
        previous.setOnClickListener { presenter.callActions(MusicManagerAction.PREVIOUS) }
        next.setOnClickListener { presenter.callActions(MusicManagerAction.NEXT) }
    }

    override fun updateTrackInfo(track: Track) {
        if(trackTitle != null) {
            trackTitle.text = track.title
            trackArtist.text = track.artist
        }
    }

    override fun updateTrackCover(coverPath: String?) {
        if(cover != null) {
            when (coverPath) {
                null -> {
                    Picasso.with(applicationContext)
                            .load(android.R.color.transparent)
                            .into(background)
                    Picasso.with(applicationContext)
                            .load(R.drawable.no_music)
                            .into(cover)
                }
                else -> {
                    Picasso.with(applicationContext)
                            .load(Uri.fromFile(File(coverPath)))
                            .transform(BlurTransformation(applicationContext))
                            .into(background)
                    Picasso.with(applicationContext)
                            .load(Uri.fromFile(File(coverPath)))
                            .into(cover)
                }
            }
        }
    }


    override fun updatePlayPause(isMusicPlaying: Boolean) {
        if(playPause != null) {
            when(isMusicPlaying) {
                true -> playPause.setImageResource(R.drawable.pause)
                false -> playPause.setImageResource(R.drawable.play)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}