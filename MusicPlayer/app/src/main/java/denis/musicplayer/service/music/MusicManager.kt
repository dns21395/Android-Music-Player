package denis.musicplayer.service.music

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.service.AppMusicService
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by denis on 10/01/2018.
 */
interface MusicManager {
    fun setService(service: AppMusicService)

    fun playTrack()
    fun pauseTrack()
    fun resumeTrack()

    fun resumePause()

    fun updateTracks(tracks: ArrayList<Track>, currentTrackPosition: Int)

    fun getCurrentTrack(): Track

    fun previousTrack()
    fun nextTrack()

    fun isPlaying(): Boolean

    fun setVolume(leftVolume: Float, rightVolume: Float)

    fun buildNotification()
    fun closeMusicPlayer()

    fun makeAction(action: MusicManagerAction)

    fun getCurrentTrackBehaviour(): BehaviorSubject<Track>
    fun actionBehaviour(): BehaviorSubject<Boolean>

    fun getTracksSize(): Int
}