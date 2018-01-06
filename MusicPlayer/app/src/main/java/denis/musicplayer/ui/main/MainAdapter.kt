package denis.musicplayer.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import denis.musicplayer.R
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.main.album.AlbumFragment
import denis.musicplayer.ui.main.artist.ArtistFragment
import denis.musicplayer.ui.main.genre.GenreFragment
import denis.musicplayer.ui.main.playlist.MainPlaylistFragment
import denis.musicplayer.ui.main.track.TrackFragment

/**
 * Created by denis on 31/12/2017.
 */
class MainAdapter(@ActivityContext val context: Context, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val fragments = arrayOf(TrackFragment.newInstance(),
                                    MainPlaylistFragment.newInstance(),
                                    AlbumFragment.newInstance(),
                                    ArtistFragment.newInstance(),
                                    GenreFragment.newInstance())

    private val names = context.resources.getStringArray(R.array.main_fragments)

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = names[position]
}