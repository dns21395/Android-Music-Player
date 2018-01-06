package denis.musicplayer.ui.main.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Genre
import denis.musicplayer.ui.main.base.MainBaseAdapter

/**
 * Created by denis on 02/01/2018.
 */
class GenreAdapter(val context: Context) : MainBaseAdapter<GenreViewHolder, Genre, GenreAdapter.Callback>()  {
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.onBind(array[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GenreViewHolder =
            GenreViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_genre, parent, false))

    interface Callback : MainBaseAdapter.Callback {

    }
}