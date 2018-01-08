package denis.musicplayer.ui.main.album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class AlbumAdapter(val context: Context) : MainBaseAdapter<AlbumViewHolder, Album, AlbumAdapter.Callback>() {
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(array[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumViewHolder =
            AlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_album, parent, false))

    interface Callback : MainBaseAdapter.Callback {

    }
}