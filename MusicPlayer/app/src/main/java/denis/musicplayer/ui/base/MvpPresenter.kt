package denis.musicplayer.ui.base

/**
 * Created by denis on 30/12/2017.
 */
interface MvpPresenter<V: MvpView> {
    fun onAttach(mvpView: V)
    fun onDetach()
}