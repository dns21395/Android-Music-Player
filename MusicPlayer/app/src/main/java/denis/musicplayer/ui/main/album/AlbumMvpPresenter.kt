package denis.musicplayer.ui.main.album

import denis.musicplayer.data.media.model.Album
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter

/**
 * Created by denis on 01/01/2018.
 */
interface AlbumMvpPresenter<V : AlbumMvpView> : MainBaseMvpPresenter<V, Album> {
    fun getAlbumTracks(albumId: Long, title: String)
}