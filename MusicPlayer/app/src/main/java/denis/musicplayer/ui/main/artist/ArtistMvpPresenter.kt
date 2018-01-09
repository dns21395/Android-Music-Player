package denis.musicplayer.ui.main.artist

import denis.musicplayer.data.media.model.Artist
import denis.musicplayer.ui.base.MvpPresenter
import denis.musicplayer.ui.main.base.MainBaseMvpPresenter
import denis.musicplayer.ui.main.base.MainBasePresenter

/**
 * Created by denis on 01/01/2018.
 */
interface ArtistMvpPresenter<V: ArtistMvpView> : MainBaseMvpPresenter<V, Artist> {
    fun getArtistTracks(artistId: Long, artistName: String)
}