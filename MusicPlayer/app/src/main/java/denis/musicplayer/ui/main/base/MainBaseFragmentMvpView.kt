package denis.musicplayer.ui.main.base



/**
 * Created by denis on 31/12/2017.
 */
interface MainBaseFragmentMvpView<A: Any> : MainBaseActivityMvpView {
    fun updateArray(array: ArrayList<A>)
}