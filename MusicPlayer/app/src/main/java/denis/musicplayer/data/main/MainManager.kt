package denis.musicplayer.data.main

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by denis on 17/01/2018.
 */
interface MainManager {
    fun callAction(action: EnumMainManager)

    fun getAction(): PublishSubject<EnumMainManager>
}