package com.test.project.di.modules

import com.test.project.features.settings.restaurants.repositories.RestaurantRepository
import com.test.project.features.settings.restaurants.repositories.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

  @Binds
  @Singleton
  abstract fun provideRestaurantRepository(interactor: RestaurantRepositoryImpl): RestaurantRepository

}