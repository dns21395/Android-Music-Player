package denis.musicplayer.ui.main.album

import android.net.Uri
import android.view.View
import com.squareup.picasso.Picasso
import denis.musicplayer.data.media.model.Album
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import denis.musicplayer.utils.ImageTransformToCircle
import kotlinx.android.synthetic.main.holder_album.view.*
import java.io.File

/**
 * Created by denis on 02/01/2018.
 */
class AlbumViewHolder(itemView: View) : MainBaseViewHolder<Album>(itemView) {
    override fun onBind(item: Album) = with(itemView) {
        title.text = item.album
        artist.text = item.artist

        when(item.art) {
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
                        .load(Uri.fromFile(File(item.art)))
                        .transform(ImageTransformToCircle())
                        .resize(96, 96)
                        .centerCrop()
                        .into(cover)
            }
        }
    }
}