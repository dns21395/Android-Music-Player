package denis.musicplayer.ui.main.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Artist
import denis.musicplayer.ui.base.BaseFragment
import denis.musicplayer.ui.main.base.MainBaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * Created by denis on 01/01/2018.
 */
class ArtistFragment : BaseFragment(), ArtistMvpView {

    companion object {
        fun newInstance(): ArtistFragment {
            val args = Bundle()
            val fragment = ArtistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var presenter: ArtistMvpPresenter<ArtistMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun setUp(view: View?) {
    }
}