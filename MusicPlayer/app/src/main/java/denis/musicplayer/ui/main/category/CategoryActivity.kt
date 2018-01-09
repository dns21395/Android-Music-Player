package denis.musicplayer.ui.main.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseActivity
import denis.musicplayer.ui.main.category.categoryfragment.CategoryFragment
import denis.musicplayer.ui.main.select.SelectFragment
import denis.musicplayer.ui.main.track.TrackFragment
import denis.musicplayer.ui.main.track.TrackMvpPresenter
import denis.musicplayer.ui.main.track.TrackMvpView
import denis.musicplayer.ui.main.updateplaylist.UpdatePlaylistDialog
import denis.musicplayer.ui.player.fragment.PlayerFragment
import denis.musicplayer.utils.BytesUtil
import javax.inject.Inject

/**
 * Created by denis on 09/01/2018.
 */
class CategoryActivity : MainBaseActivity(), CategoryMvpView {

    companion object {
        fun getStartIntent(context: Context, tracks: ArrayList<Track>): Intent {

            val intent = Intent(context, CategoryActivity::class.java)

            val args = Bundle()
            args.putByteArray(CategoryFragment.KEY_TRACKS, BytesUtil.toByteArray(tracks))

            intent.putExtras(args)

            return intent
        }
    }

    @Inject lateinit var presenter: CategoryMvpPresenter<CategoryMvpView>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_category)

        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        supportFragmentManager.beginTransaction().replace(R.id.bottomFrame, CategoryFragment.newInstance(getTracks())).commit()
        replaceFragment(PlayerFragment.newInstance())
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.bottomFrame, fragment).commit()
    }

    override fun showSelectFragment() {
        replaceFragment(SelectFragment.newInstance())
    }

    override fun hideSelectFragment() {
        replaceFragment(PlayerFragment.newInstance())
    }

    override fun updateCountSelectFragment(count: Int) {
        val fragment = supportFragmentManager?.findFragmentById(R.id.bottomFrame) as SelectFragment?

        fragment?.updateCount(count)
    }

    override fun showUpdatePlaylist(array: ArrayList<Track>) {
        UpdatePlaylistDialog.newInstance(array).show(supportFragmentManager, UpdatePlaylistDialog.TAG)
    }

    private fun getTracks(): ArrayList<Track> {
        val objects = intent.getByteArrayExtra(CategoryFragment.KEY_TRACKS)
        return when(objects) {
            null -> ArrayList()
            else -> BytesUtil.toObject(objects)
        }
    }
}