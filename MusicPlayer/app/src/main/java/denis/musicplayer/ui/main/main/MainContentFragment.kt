package denis.musicplayer.ui.main.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.MainAdapter
import denis.musicplayer.ui.main.base.MainBaseFragment
import denis.musicplayer.utils.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_main_content.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class MainContentFragment : BaseFragment(), MainContentMvpView {

    companion object {
        fun newInstance(): MainContentFragment {
            val args = Bundle()
            val fragment = MainContentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: MainContentMvpPresenter<MainContentMvpView>

    @Inject lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_content, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
        viewPager.adapter = adapter
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            var currentPage = 0

            override fun onPageScrollStateChanged(state: Int) {
                if(state == ViewPager.SCROLL_STATE_IDLE && currentPage != viewPager.currentItem) {
                    currentPage = viewPager.currentItem
                    presenter.callActionClear()
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {}
        })

    }

}