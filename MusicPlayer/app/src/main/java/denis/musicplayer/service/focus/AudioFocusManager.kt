package denis.musicplayer.service.focus

import denis.musicplayer.service.music.AppMusicManager
import denis.musicplayer.service.music.MusicManager

/**
 * Created by denis on 16/01/2018.
 */
interface AudioFocusManager {
    fun requestAudioFocus()
    fun abandonAudioFocus()
    fun setMusicManager(musicManager: MusicManager)

}