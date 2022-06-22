package com.test.project.features.developer_settings.di

import com.test.project.di.modules.NetworkModule
import com.test.project.shared.api.interceptors.HeaderInterceptor
import com.test.project.shared.api.interceptors.Interceptors
import com.test.project.shared.api.interceptors.NetworkInterceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

  @Singleton
  @Provides
  fun provideOkHttpInterceptors(headerInterceptor: HeaderInterceptor): Interceptors =
    Interceptors(listOf(headerInterceptor))

  @Singleton
  @Provides
  fun provideOkHttpNetworkInterceptors(): NetworkInterceptors = NetworkInterceptors(listOf())

  @Singleton
  @Provides
  @Named(NetworkModule.WRITE_TIMEOUT)
  fun provideWriteTimeout(): Int = 30

  @Singleton
  @Provides
  @Named(NetworkModule.READ_TIMEOUT)
  fun provideReadTimeout(): Int = 30

  @Singleton
  @Provides
  @Named(NetworkModule.CONNECT_TIMEOUT)
  fun provideConnectTimeout(): Int = 30
}