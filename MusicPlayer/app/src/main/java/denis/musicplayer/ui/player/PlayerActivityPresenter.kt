package denis.musicplayer.ui.player

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.service.music.MusicManager
import denis.musicplayer.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 14/01/2018.
 */
class PlayerActivityPresenter<V: PlayerActivityMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val musicManager: MusicManager)
    : BasePresenter<V>(context, dataManager, compositeDisposable), PlayerActivityMvpPresenter<V> {

}