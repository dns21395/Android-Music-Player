package denis.musicplayer.data.media.model

import java.io.Serializable

/**
 * Created by denis on 01/01/2018.
 */
data class Track(val id: Long,
                 val title: String,
                 val artist: String,
                 val data: String,
                 val duration: String,
                 val albumId: Long) : Serializable {
    companion object {
        fun convertDuration(value: Long): String {
            val hrs = value / 3600000
            val mns = value / 60000 % 60000
            val scs = value % 60000 / 1000

            return when (hrs) {
                0L -> String.format("%02d:%02d", mns, scs)
                else -> String.format("%02d:%02d:%02d", hrs, mns, scs)
            }
        }
    }
}