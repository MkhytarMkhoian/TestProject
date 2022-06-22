package com.test.project.features.developer_settings.di

import com.test.project.features.developer_settings.*
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeveloperSettingsModule {

  @Singleton
  @Provides
  fun provideDeveloperSettingsModel(impl: DeveloperSettingsModelImpl): DeveloperSettingsModel = impl

  @Singleton
  @Provides
  fun providesFlipperLinkHandler(
    flipperLinkHandler: FlipperLinkHandlerImpl
  ): FlipperLinkHandler = flipperLinkHandler

  @Singleton
  @Provides
  fun providesFlipper(
    flipperOkhttpInterceptor: FlipperOkhttpInterceptor,
    plugin: NetworkFlipperPlugin
  ): Flipper {
    return FlipperImpl(plugin, flipperOkhttpInterceptor)
  }
}