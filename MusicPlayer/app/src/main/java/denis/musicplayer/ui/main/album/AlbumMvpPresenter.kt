package denis.musicplayer.ui.main.album

import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 01/01/2018.
 */
interface AlbumMvpPresenter<V : AlbumMvpView> : MainBaseMvpPresenter<V> {
    fun getAlbums()
}