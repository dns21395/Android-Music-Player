package denis.musicplayer.service.music

import denis.musicplayer.data.media.model.Track

/**
 * Created by denis on 10/01/2018.
 */
interface MusicManager {
    fun playTrack()

    fun updateTracks(tracks: ArrayList<Track>, currentTrackPosition: Int)

    fun getCurrentTrack(): Track

    fun nextTrack()

    fun buildNotification()
}