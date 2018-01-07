package denis.musicplayer.data.media.model

/**
 * Created by denis on 01/01/2018.
 */
data class Genre(val id: Long,
                 val name: String) {
    override fun equals(other: Any?): Boolean {
        return id == (other as Genre).id
    }
}