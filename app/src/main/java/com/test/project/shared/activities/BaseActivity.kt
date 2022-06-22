package com.test.project.shared.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.test.project.app.AppConfig
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

  @Inject lateinit var appConfig: AppConfig

  override fun onCreate(savedInstanceState: Bundle?) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    super.onCreate(savedInstanceState)
    appConfig.onActivityContextAvailable(this)
    appConfig.onConfigChanged(this, null)
  }

  override fun onDestroy() {
    super.onDestroy()
    appConfig.onActivityContextUnAvailable()
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    appConfig.onConfigChanged(this, newConfig)
    super.onConfigurationChanged(newConfig)
    appConfig.onActivityContextAvailable(this)
  }
}
