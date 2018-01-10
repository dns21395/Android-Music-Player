package denis.musicplayer.ui.main.genre

import android.view.View
import denis.musicplayer.data.media.model.Genre
import denis.musicplayer.ui.main.base.MainBaseViewHolder
import kotlinx.android.synthetic.main.holder_genre.view.*

/**
 * Created by denis on 02/01/2018.
 */
class GenreViewHolder(itemView: View) : MainBaseViewHolder<Genre>(itemView) {
    override fun onBind(item: Genre) = with(itemView) {
        genreTitle.text = item.name
    }
}