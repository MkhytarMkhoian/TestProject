package com.test.project.app

import com.test.project.features.developer_settings.TaggableDebugTree
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
  lateinit var flipperInteractor: FlipperInteractor

  override fun onCreate() {
    super.onCreate()
    instance = this
    initTimber()
    flipperInteractor.setup(this)
  }

  override fun isDebug(): Boolean = true

  private fun initTimber() {
    // unique tag prefix for easy filtering on some devices
    Timber.plant(TaggableDebugTree("Allset"))
  }
}
