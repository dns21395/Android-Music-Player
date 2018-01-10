package denis.musicplayer.ui.main.genre

import denis.musicplayer.data.media.model.Genre
import denis.musicplayer.ui.base.MvpPresenter
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 01/01/2018.
 */
interface GenreMvpPresenter<V : GenreMvpView> : MainBaseMvpPresenter<V, Genre> {
    fun getGenreTracks(genreID: Long, title: String)
}