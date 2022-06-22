package com.test.project.app

import com.test.project.features.developer_settings.Flipper
import com.test.project.features.developer_settings.TaggableDebugTree
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : BaseApp() {

  companion object {
    @JvmStatic
    lateinit var instance: App
  }

  @Inject
  lateinit var flipper: Flipper

  override fun onCreate() {
    super.onCreate()
    instance = this
    initTimber()
    initFlipper()
  }

  override fun isDebug(): Boolean = true

  private fun initFlipper() {
    SoLoader.init(this, false)

    if (FlipperUtils.shouldEnableFlipper(this)) {
      val client = AndroidFlipperClient.getInstance(this)
      flipper.addNetworkFlipperPlugin(client)
      client.start()
    }
  }

  private fun initTimber() {
    // unique tag prefix for easy filtering on some devices
    Timber.plant(TaggableDebugTree("Allset"))
  }
}
