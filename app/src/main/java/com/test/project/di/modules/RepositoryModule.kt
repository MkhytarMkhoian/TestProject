package com.test.project.di.modules

import com.test.project.shared.api.AppApiRepository
import com.test.project.shared.api.AppApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Singleton
  @Provides
  fun provideAppApiRepository(impl: AppApiRepositoryImpl): AppApiRepository = impl
}