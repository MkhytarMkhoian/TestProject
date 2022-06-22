package com.test.project.app

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApp() {

  companion object {
    @JvmStatic lateinit var instance: App
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  override fun isDebug(): Boolean = false
}
