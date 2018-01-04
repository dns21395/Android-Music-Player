package denis.musicplayer.di

import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Created by denis on 30/12/2017.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentContext

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PreferenceInfo

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerService
