package denis.musicplayer.data.select

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by denis on 15/01/2018.
 */
interface SelectManager {
    fun callAction(action: EnumSelectManager)

    fun getActions(): BehaviorSubject<EnumSelectManager>

    fun updateSelectedItemsSize(size: Int)

    fun getSelectedItemsSize(): BehaviorSubject<Int>
}