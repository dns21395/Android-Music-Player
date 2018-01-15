package denis.musicplayer.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.base.BaseActivity
import denis.musicplayer.ui.main.base.MainBaseActivity
import denis.musicplayer.ui.main.base.MainBaseActivityMvpView
import denis.musicplayer.ui.main.main.MainContentFragment
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import denis.musicplayer.ui.player.fragment.PlayerFragment
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class MainActivity : MainBaseActivity(), MainMvpView {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private val TAG = "MainActivity"

    @Inject lateinit var presenter: MainMvpPresenter<MainMvpView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        presenter.onAttach(this)

        supportFragmentManager.beginTransaction().replace(R.id.mainContentFragment, MainContentFragment.newInstance()).commit()

        replaceFragment(PlayerFragment.newInstance())

        setUp()
    }

    override fun setUp() {

    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    override fun showSelectFragment() {
        if(getBottomFrameLayout() !is SelectFragment) replaceFragment(SelectFragment.newInstance())
    }

    override fun hideSelectFragment() {
        if(getBottomFrameLayout() !is PlayerFragment) replaceFragment(PlayerFragment.newInstance())
    }

    override fun showUpdatePlaylist(array: ArrayList<Track>) {
        UpdatePlaylistDialog.newInstance(array).show(supportFragmentManager, UpdatePlaylistDialog.TAG)
    }

    private fun getBottomFrameLayout(): Fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
}