package com.test.project.app

import android.app.Application
import android.content.res.Configuration
import javax.inject.Inject

abstract class BaseApp : Application() {

  @Inject
  lateinit var appConfig: AppConfig

  abstract fun isDebug(): Boolean

  override fun onCreate() {
    super.onCreate()
    appConfig.init(this)
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    try {
      appConfig.onConfigChanged(applicationContext, newConfig)
    } catch (e: Exception) {
      // Do nothing
    }
  }
}
