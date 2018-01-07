package denis.musicplayer.data.media.model

import java.io.Serializable

/**
 * Created by denis on 01/01/2018.
 */
data class Track(val id: Int,
                 val title: String,
                 val artist: String,
                 val data: String,
                 val duration: String,
                 val albumId: Int) : Serializable