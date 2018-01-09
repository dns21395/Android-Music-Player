package denis.musicplayer.ui.main.category.categoryfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.ui.main.base.MainBaseAdapter


/**
 * Created by denis on 09/01/2018.
 */
class CategoryFragmentAdapter(val context: Context) : MainBaseAdapter<CategoryFragmentViewHolder,
        Track,
        CategoryFragmentMvpView,
        CategoryFragmentMvpPresenter<CategoryFragmentMvpView>>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryFragmentViewHolder =
            CategoryFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_track, parent, false))


}