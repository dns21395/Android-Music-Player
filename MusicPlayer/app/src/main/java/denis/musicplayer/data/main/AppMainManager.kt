package denis.musicplayer.data.main

import denis.musicplayer.di.PerActivity
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by denis on 17/01/2018.
 */
@PerActivity
class AppMainManager @Inject constructor(): MainManager {
    private val action: PublishSubject<EnumMainManager> = PublishSubject.create()

    override fun callAction(action: EnumMainManager) {
        this.action.onNext(action)
    }

    override fun getAction(): PublishSubject<EnumMainManager> = action

}

enum class EnumMainManager {
    UPDATE_PLAYLIST
}