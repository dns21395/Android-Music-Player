package denis.musicplayer.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
import denis.musicplayer.ui.main.main.MainContentFragment
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.player.fragment.PlayerFragment
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class MainActivity : BaseActivity(), MainMvpView {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }



    private val TAG = "MainActivity"

    @Inject lateinit var presenter: MainMvpPresenter<MainMvpView>

    var selectFragment: Fragment = SelectFragment.newInstance()
    var playerFragment: Fragment = PlayerFragment.newInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        presenter.onAttach(this)

        supportFragmentManager.beginTransaction().replace(R.id.mainContentFragment, MainContentFragment.newInstance()).commit()
        replaceFragment(playerFragment)

        setUp()
    }

    override fun setUp() {

    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

}