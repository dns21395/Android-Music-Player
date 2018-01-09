package denis.musicplayer.ui.main.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Artist
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class ArtistAdapter(val context: Context) : MainBaseAdapter<ArtistViewHolder, Artist, ArtistMvpView, ArtistMvpPresenter<ArtistMvpView>>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArtistViewHolder =
            ArtistViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_artist, parent, false))

}