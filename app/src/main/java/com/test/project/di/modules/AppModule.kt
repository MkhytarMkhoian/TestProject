package com.test.project.di.modules

import android.app.Application
import android.content.Context
import com.test.project.app.App
import com.test.project.app.navigation.AppNavigator
import com.test.project.app.navigation.AppNavigatorImpl
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.coroutines.CoroutineDispatchersImpl
import com.test.project.shared.resources.ResStorage
import com.test.project.shared.resources.ResStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  fun provideApp(application: Application): App {
    return application as App
  }

  @Singleton
  @Provides
  fun provideContext(app: App): Context = app

  @Singleton
  @Provides
  fun provideStringResStorage(storage: ResStorageImpl): ResStorage = storage

  @Singleton
  @Provides
  fun provideCoroutineDispatchers(coroutineDispatchers: CoroutineDispatchersImpl): CoroutineDispatchers =
    coroutineDispatchers

  @Provides
  @Singleton
  fun provideAppNavigator(navigatorImpl: AppNavigatorImpl): AppNavigator = navigatorImpl
}