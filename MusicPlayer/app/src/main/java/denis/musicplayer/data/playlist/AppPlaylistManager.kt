package denis.musicplayer.data.playlist

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import denis.musicplayer.R
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
                val id: Int = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID)).toInt()
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

    override fun deletePlaylist(id: Int) {
        val playlistId = id.toString()
        val resolver = context.contentResolver
        val where = MediaStore.Audio.Playlists._ID + "=?"
        val whereVal = arrayOf(playlistId)
        resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, where, whereVal)
    }
}