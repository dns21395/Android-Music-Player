package denis.musicplayer.data.media

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import denis.musicplayer.data.media.model.*
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by denis on 01/01/2018.
 */
@Singleton
class AppMediaManager
    @Inject constructor(@ApplicationContext val context: Context)
    : MediaManager {

    override fun scanTracks(): ArrayList<Track> {
        val array = ArrayList<Track>()

        val uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID)

        val selection = "${MediaStore.Audio.Media.IS_MUSIC}  != 0"
        val sortOrder = "${MediaStore.Audio.AudioColumns.TITLE} COLLATE LOCALIZED ASC"

        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()

            while(!cursor.isAfterLast) {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)).toInt()
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val duration = convertDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)).toLong())
                val albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toInt()

                cursor.moveToNext()

                array.add(Track(id, title, artist, data, duration, albumId))
            }

            cursor.close()
        }

        return array
    }

    override fun scanAlbums(): ArrayList<Album> {
        val array = ArrayList<Album>()

        val uri: Uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART)

        val sortOrder = "${MediaStore.Audio.Media.ALBUM} ASC"

        val cursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)).toInt()
                val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))

                cursor.moveToNext()
                array.add(Album(id, album, artist, cover))
            }
            cursor.close()
        }

        return array
    }

    override fun scanArtists(): ArrayList<Artist> {
        val array = ArrayList<Artist>()

        val uri: Uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        )

        val sortOrder = "${MediaStore.Audio.Artists.ARTIST} ASC"

        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)).toInt()
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST))
                val albumsCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS))
                val tracksCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))

                cursor.moveToNext()

                array.add(Artist(id, artist, albumsCount, tracksCount))
            }

            cursor.close()
        }

        return array
    }

    override fun scanGenres(): ArrayList<Genre> {
        val array = ArrayList<Genre>()

        val uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
                MediaStore.Audio.Genres._ID,
                MediaStore.Audio.Genres.NAME
        )

        val sortOrder = "${MediaStore.Audio.Genres.NAME} ASC"

        val cursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val id: Int = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres._ID)).toInt()
                val name: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME))

                cursor.moveToNext()

                array.add(Genre(id, name))
            }

            cursor.close()
        }

        return array
    }

    private fun convertDuration(value: Long): String {
        val hrs = value / 3600000
        val mns = value / 60000 % 60000
        val scs = value % 60000 / 1000

        return when(hrs) {
            0L -> String.format("%02d:%02d", mns, scs)
            else -> String.format("%02d:%02d:%02d", hrs, mns, scs)
        }
    }

}