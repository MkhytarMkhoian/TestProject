package com.test.project.di.modules

import com.test.project.app.App
import com.test.project.shared.api.AppApi
import com.test.project.shared.api.TokenApi
import com.test.project.shared.api.UrlManager
import com.test.project.shared.api.interceptors.Interceptors
import com.test.project.shared.api.interceptors.NetworkInterceptors
import com.test.project.shared.api.interceptors.UserTokenAuthenticator
import com.test.project.shared.api.network.NetworkManager
import com.test.project.shared.api.network.NetworkManagerImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  const val WRITE_TIMEOUT = "WRITE_TIMEOUT"
  const val READ_TIMEOUT = "READ_TIMEOUT"
  const val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"

  @Singleton
  @Provides
  fun provideHttpClient(
    iterceptors: Interceptors,
    networkInterceptors: NetworkInterceptors,
    @Named(WRITE_TIMEOUT) writeTimeout: Int,
    @Named(READ_TIMEOUT) readTimeout: Int,
    @Named(CONNECT_TIMEOUT) connectTimeout: Int,
    authenticator: UserTokenAuthenticator,
  ): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.authenticator(authenticator)
    builder.readTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
    builder.writeTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
    builder.connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)

    for (iterceptor in iterceptors.items) {
      builder.addInterceptor(iterceptor)
    }
    for (networkInterceptor in networkInterceptors.items) {
      builder.addNetworkInterceptor(networkInterceptor)
    }
    return builder.build()
  }

  @Singleton
  @Provides
  fun provideMerchantApiService(
    client: Lazy<OkHttpClient>,
    moshi: Moshi,
    urlManager: UrlManager
  ): AppApi {
    return Retrofit.Builder()
      .baseUrl(urlManager.getApiBaseUrl())
      .addConverterFactory(
        MoshiConverterFactory.create(moshi)
          .withNullSerialization()
          .asLenient()
      )
      .callFactory { request -> client.get().newCall(request) }
      .validateEagerly(App.instance.isDebug())
      .build()
      .create(AppApi::class.java)
  }

  @Provides
  @Singleton
  fun provideTokenApiService(
    client: Lazy<OkHttpClient>,
    moshi: Moshi,
    urlManager: UrlManager
  ): TokenApi {
    return Retrofit.Builder()
      .baseUrl(urlManager.getTokenBaseUrl())
      .addConverterFactory(
        MoshiConverterFactory.create(moshi)
          .withNullSerialization()
          .asLenient()
      )
      .callFactory { request -> client.get().newCall(request) }
      .validateEagerly(App.instance.isDebug())
      .build()
      .create(TokenApi::class.java)
  }

  @Provides
  @Singleton
  fun provideNetworkManager(manager: NetworkManagerImpl): NetworkManager = manager

  @Singleton
  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }
}