package denis.musicplayer.ui.splash

import denis.musicplayer.di.PerActivity
import denis.musicplayer.ui.base.MvpPresenter

/**
 * Created by denis on 30/12/2017.
 */
@PerActivity
interface SplashMvpPresenter<V: SplashMvpView> : MvpPresenter<V> {
}