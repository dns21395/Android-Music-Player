package denis.musicplayer.ui.main.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseActivity
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragment
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import denis.musicplayer.ui.player.PlayerActivity
import denis.musicplayer.ui.player.fragment.PlayerFragment
import denis.musicplayer.utils.BytesUtil
import kotlinx.android.synthetic.main.activity_category.*
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryActivity : MainBaseActivity(), CategoryMvpView {

    private val TAG = "CategoryActivity"

    companion object {
        val KEY_TITLE = "key_title"

        fun getStartIntent(context: Context, tracks: ArrayList<Track>, title: String): Intent {

            val intent = Intent(context, CategoryActivity::class.java)

            val args = Bundle()
            args.putByteArray(CategoryFragment.KEY_TRACKS, BytesUtil.toByteArray(tracks))
            args.putString(KEY_TITLE, title)

            intent.putExtras(args)

            return intent
        }
    }

    @Inject lateinit var presenter: CategoryMvpPresenter<CategoryMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        categoryTitle.text = intent.getStringExtra(KEY_TITLE)
        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, CategoryFragment.newInstance(getTracks())).commit()
        replaceFragment(PlayerFragment.newInstance())

        back.setOnClickListener {
            finish()
        }
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.bottomFrame, fragment).commit()
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

    private fun getTracks(): ArrayList<Track> {
        val objects = intent.getByteArrayExtra(CategoryFragment.KEY_TRACKS)

        return when(objects) {
            null -> ArrayList()
            else -> BytesUtil.toObjectArray(objects)
        }
    }

    private fun getBottomFrameLayout(): Fragment = supportFragmentManager.findFragmentById(R.id.bottomFrame)


}