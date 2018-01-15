package denis.musicplayer.ui.main.base

import denis.musicplayer.di.PerActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by denis on 04/01/2018.
 */
@PerActivity
class MainRxBus {
    private val bus = PublishSubject.create<MainEnumRxBus>()

    fun send(event: MainEnumRxBus) {
        bus.onNext(event)
    }

    fun toObservable(): Observable<MainEnumRxBus> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}

enum class MainEnumRxBus {
    UPDATE_PLAYLIST,
}

