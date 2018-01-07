package denis.musicplayer.data.playlist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import denis.musicplayer.R
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.data.playlist.model.Playlist
import denis.musicplayer.di.ApplicationContext
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by denis on 03/01/2018.
 */
@Singleton
class AppPlaylistManager
    @Inject constructor(@ApplicationContext val context: Context) : PlaylistManager {

    private val TAG = "AppPlaylistManager"

    override fun scanPlaylist(): ArrayList<Playlist> {
        val array = ArrayList<Playlist>()

        val uri  = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
                MediaStore.Audio.Playlists._ID,
                MediaStore.Audio.Playlists.NAME
        )

        val sortOrder = "${MediaStore.Audio.Playlists.NAME} ASC"

        val cursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val id  = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID)).toLong()
                val name: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME))

                cursor.moveToNext()

                array.add(Playlist(id, name))
            }

            cursor.close()
        }

        return array
    }

    override fun createPlaylist(name: String) {
        val resolver = context.contentResolver
        val values = ContentValues(1)
        values.put(MediaStore.Audio.Playlists.NAME, name)
        val uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        resolver.insert(uri, values)
    }

    override fun deletePlaylist(id: Long) {
        val playlistId = id.toString()
        val resolver = context.contentResolver
        val where = MediaStore.Audio.Playlists._ID + "=?"
        val whereVal = arrayOf(playlistId)
        resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, where, whereVal)
    }

    override fun addTracksToPlaylist(id: Long, tracks: ArrayList<Track>) {
        val count = getPlaylistSize(id)
        val values = arrayOfNulls<ContentValues>(tracks.size)

        Log.d(TAG, "count : $count")

        for (i in tracks.indices) {
            values[i] = ContentValues()
            values[i]?.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, i + count + 1)
            values[i]?.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, tracks[i].id)
        }


        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id)
        val resolver = context.contentResolver
        resolver.bulkInsert(uri, values)
        resolver.notifyChange(Uri.parse("content://media"), null)
    }

    private fun getPlaylistSize(id: Long): Int {
        var count = 0
        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id)

        val projection = arrayOf(MediaStore.Audio.Playlists.Members._ID)
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val cursor: Cursor? = context.contentResolver.query(uri, projection, selection, null, null)

        if (cursor != null) {
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {
                count++
                cursor.moveToNext()
            }

            cursor.close()
        }

        return count
    }

    override fun getPlaylistTracks(id: Long): ArrayList<Track> {
        val array = ArrayList<Track>()

        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id)
        val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID)
        val selection = "${MediaStore.Audio.Media.IS_MUSIC}  != 0"
        val sortOrder = "${MediaStore.Audio.Playlists.Members.PLAY_ORDER} ASC"

        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        if(cursor != null) {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                val trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)).toLong()
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val duration = Track.convertDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)).toLong())
                val albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toLong()

                array.add(Track(trackId, title, artist, data, duration, albumId))

                cursor.moveToNext()
            }

            cursor.close()
        }

        return array
    }

    override fun deletePlaylistTrack(playlistId: Long, trackId: Long) {
        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId)
        val where = MediaStore.Audio.Playlists.Members._ID + "=?"
        val whereval = arrayOf(trackId.toString())
        context.contentResolver.delete(uri, where, whereval)
    }

    override fun playlistItemReorder(playlistId: Long, oldPos: Int, newPos: Int) {
        MediaStore.Audio.Playlists.Members.moveItem(context.contentResolver, playlistId, oldPos, newPos)
    }
}