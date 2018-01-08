package denis.musicplayer.ui.main.album

import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.View
import com.squareup.picasso.Picasso
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import denis.musicplayer.utils.ImageTransformToCircle
import kotlinx.android.synthetic.main.holder_album.view.*
import java.io.File

/**
 * Created by denis on 02/01/2018.
 */
class AlbumViewHolder(itemView: View) : MainBaseViewHolder(itemView) {
    fun onBind(album: Album) = with(itemView) {
        title.text = album.album
        artist.text = album.artist

        when(album.art) {
            null -> {
                Picasso.with(context)
                        .load(Uri.parse("android.resource://gabyshev.denis.musicplayer/drawable/no_music"))
                        .transform(ImageTransformToCircle())
                        .resize(96, 96)
                        .centerCrop()
                        .into(cover)
            }
            else -> {
                Picasso.with(context)
                        .load(Uri.fromFile(File(album.art)))
                        .transform(ImageTransformToCircle())
                        .resize(96, 96)
                        .centerCrop()
                        .into(cover)
            }
        }
    }


}