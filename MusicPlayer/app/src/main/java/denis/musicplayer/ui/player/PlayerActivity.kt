package denis.musicplayer.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
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

    }
}