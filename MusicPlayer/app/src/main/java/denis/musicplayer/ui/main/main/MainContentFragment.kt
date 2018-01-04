package denis.musicplayer.ui.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.MainAdapter
import denis.musicplayer.ui.main.base.MainBaseFragment
import kotlinx.android.synthetic.main.fragment_main_content.*
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
        tabLayout.setupWithViewPager(viewPager)
    }

}