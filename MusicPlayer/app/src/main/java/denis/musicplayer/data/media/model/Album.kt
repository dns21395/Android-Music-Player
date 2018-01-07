package denis.musicplayer.data.media.model

/**
 * Created by denis on 01/01/2018.
 */
data class Album(val id: Long,
                 val album: String,
                 val artist: String,
                 val art: String?) {


    override fun equals(other: Any?): Boolean {
        return id == (other as Album).id
    }
}