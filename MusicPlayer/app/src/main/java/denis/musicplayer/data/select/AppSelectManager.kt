package denis.musicplayer.data.select

import denis.musicplayer.di.PerActivity
import denis.musicplayer.ui.main.base.MainEnumRxBus
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by denis on 15/01/2018.
 */
@PerActivity
class AppSelectManager @Inject constructor() : SelectManager {
    private var selectedItemsSize: BehaviorSubject<Int> = BehaviorSubject.create()
    private val action: BehaviorSubject<EnumSelectManager> = BehaviorSubject.create()

    override fun callAction(action: EnumSelectManager) {
        this.action.onNext(action)
    }

    override fun getActions(): BehaviorSubject<EnumSelectManager> = action

    override fun updateSelectedItemsSize(size: Int) {
        selectedItemsSize.onNext(size)
    }

    override fun getSelectedItemsSize(): BehaviorSubject<Int> = selectedItemsSize
}

enum class EnumSelectManager {
    INSERT_ITEMS,
    CLEAR_ITEMS,
}