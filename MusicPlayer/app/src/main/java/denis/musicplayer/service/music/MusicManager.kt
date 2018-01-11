package denis.musicplayer.service.music

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.service.AppMusicService

/**
 * Created by denis on 10/01/2018.
 */
interface MusicManager {
    fun setService(service: AppMusicService)

    fun playTrack()
    fun pauseTrack()

    fun updateTracks(tracks: ArrayList<Track>, currentTrackPosition: Int)

    fun getCurrentTrack(): Track

    fun previousTrack()
    fun nextTrack()

    fun isPlaying(): Boolean

    fun buildNotification()
    fun closeMusicPlayer()


}