package denis.musicplayer.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by denis on 30/12/2017.
 */
class MainActivity : BaseActivity(), MainMvpView {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @Inject lateinit var presenter: MainMvpPresenter<MainMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {

    }
}