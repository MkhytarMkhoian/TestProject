package com.test.project.features.developer_settings.di

import com.test.project.features.developer_settings.*
import com.test.project.shared.navigation.DevSettingNavigation
import com.test.project.shared.navigation.DevSettingNavigationNoImpl
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
  fun providesDevSettingNavigation(
    devSettingNavigation: DevSettingNavigationNoImpl
  ): DevSettingNavigation = devSettingNavigation

  @Singleton
  @Provides
  fun providesFlipperLinkHandler(
    flipperLinkHandler: FlipperLinkHandlerImpl
  ): FlipperLinkHandler = flipperLinkHandler

  @Singleton
  @Provides
  fun providesFlipper(flipper: FlipperImpl): Flipper = flipper

  @Singleton
  @Provides
  fun provideDeveloperSettingsModel(): DeveloperSettingsModel {
    return object : DeveloperSettingsModel {
      override fun apply() {
      }
    }
  }
}