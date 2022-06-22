package com.test.project.di.modules

import com.test.project.features.auth.interactors.RefreshTokenInteractor
import com.test.project.features.auth.interactors.RefreshTokenInteractorImpl
import com.test.project.features.auth.repositories.TokenRepository
import com.test.project.features.auth.repositories.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

  @Binds
  abstract fun provideRefreshTokenInteractor(interactor: RefreshTokenInteractorImpl): RefreshTokenInteractor

  @Binds
  @Singleton
  abstract fun provideRepository(interactor: TokenRepositoryImpl): TokenRepository
}