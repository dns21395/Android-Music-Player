package denis.musicplayer.ui.main.select

import android.content.Context
import denis.musicplayer.data.DataManager
import denis.musicplayer.di.ActivityContext
import denis.musicplayer.ui.base.BasePresenter
import denis.musicplayer.ui.main.base.MainEnumRxBus
import denis.musicplayer.ui.main.base.MainRxBus
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by denis on 06/01/2018.
 */
class SelectPresenter<V: SelectMvpView>
    @Inject constructor(@ActivityContext context: Context,
                        dataManager: DataManager,
                        compositeDisposable: CompositeDisposable,
                        val rxBus: MainRxBus)
    : BasePresenter<V>(context, dataManager, compositeDisposable), SelectMvpPresenter<V> {

    override fun sendMessageToUpdatePlaylist() {
        rxBus.send(MainEnumRxBus.SHOW_UPDATE_PLAYLIST_DIALOG)
    }
}