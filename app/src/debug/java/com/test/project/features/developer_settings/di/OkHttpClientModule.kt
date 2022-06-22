package com.test.project.features.developer_settings.di

import com.test.project.di.modules.NetworkModule
import com.test.project.features.developer_settings.CurlInterceptor
import com.test.project.shared.api.interceptors.HeaderInterceptor
import com.test.project.shared.api.interceptors.Interceptors
import com.test.project.shared.api.interceptors.NetworkInterceptors
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

  @Singleton
  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(Timber::d)
    interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    return interceptor
  }

  @Singleton
  @Provides
  fun provideOkHttpInterceptors(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    headerInterceptor: HeaderInterceptor,
    curlInterceptor: CurlInterceptor
  ): Interceptors {
    return Interceptors(listOf(headerInterceptor, httpLoggingInterceptor, curlInterceptor))
  }

  @Singleton
  @Provides
  fun provideOkHttpNetworkInterceptors(
    flipperOkhttpInterceptor: FlipperOkhttpInterceptor
  ): NetworkInterceptors {
    return NetworkInterceptors(listOf(flipperOkhttpInterceptor))
  }

  @Singleton
  @Provides
  fun provideFlipperOkhttpInterceptor(plugin: NetworkFlipperPlugin): FlipperOkhttpInterceptor {
    return FlipperOkhttpInterceptor(plugin)
  }

  @Singleton
  @Provides
  fun provideNetworkFlipperPlugin(): NetworkFlipperPlugin {
    return NetworkFlipperPlugin()
  }

  @Singleton
  @Provides
  @Named(NetworkModule.WRITE_TIMEOUT)
  fun provideWriteTimeout(): Int {
    return 30
  }

  @Singleton
  @Provides
  @Named(NetworkModule.READ_TIMEOUT)
  fun provideReadTimeout(): Int {
    return 30
  }

  @Singleton
  @Provides
  @Named(NetworkModule.CONNECT_TIMEOUT)
  fun provideConnectTimeout(): Int {
    return 30
  }
}