package denis.musicplayer.ui.player

import denis.musicplayer.data.media.model.Track
import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.MvpView

/**
 * Created by denis on 14/01/2018.
 */
interface PlayerActivityMvpView : MvpView {
    fun updateTrackInfo(track: Track)

    fun updateTrackCover(coverPath: String?)

    fun updatePlayPause(isMusicPlaying: Boolean)
}