package com.test.project.app

import android.content.res.Configuration
import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.jakewharton.threetenabp.AndroidThreeTen
import javax.inject.Inject

abstract class BaseApp : MultiDexApplication() {

  @Volatile
  private lateinit var applicationHandler: Handler

  @Inject
  lateinit var appConfig: AppConfig

  abstract fun isDebug(): Boolean

  override fun onCreate() {
    super.onCreate()
    applicationHandler = Handler(applicationContext.mainLooper)
    appConfig.init(this)
    initAndroidThreeTen()
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    try {
      Glide.get(this).onConfigurationChanged(newConfig)
      appConfig.onConfigChanged(applicationContext, newConfig)
    } catch (e: Exception) {
      // Do nothing
    }
  }

  override fun onLowMemory() {
    super.onLowMemory()
    Glide.get(this).setMemoryCategory(MemoryCategory.LOW)
  }

  override fun onTrimMemory(level: Int) {
    super.onTrimMemory(level)
    Glide.get(this).trimMemory(level)
  }

  private fun initAndroidThreeTen() {
    AndroidThreeTen.init(this)
  }

  fun runOnUIThread(runnable: Runnable) {
    runOnUIThread(runnable, 0L)
  }

  fun runOnUIThread(runnable: Runnable, delay: Long) {
    if (delay == 0L) {
      applicationHandler.post(runnable)
    } else {
      applicationHandler.postDelayed(runnable, delay)
    }
  }

  fun cancelRunOnUIThread(runnable: Runnable) {
    applicationHandler.removeCallbacks(runnable)
  }
}
