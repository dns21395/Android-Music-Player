package denis.musicplayer.ui.player

import denis.musicplayer.service.music.MusicManagerAction
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 14/01/2018.
 */
interface PlayerActivityMvpPresenter<V: PlayerActivityMvpView> : MvpPresenter<V> {
    fun getCurrentTrack()

    fun getCover(albumID: Long)

    fun getActionPlayPause()

    fun callActions(action: MusicManagerAction)
}