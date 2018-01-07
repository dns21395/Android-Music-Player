package denis.musicplayer.data.media.model

/**
 * Created by denis on 01/01/2018.
 */
data class Artist(val id: Long,
                  val artist: String,
                  val albumCount: String,
                  val trackCount: String) {
    override fun equals(other: Any?): Boolean {
        return id == (other as Artist).id
    }
}