package com.test.project.app

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import com.test.project.shared.resources.ResStorage
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.ceil

class AppConfig
@Inject constructor(
  private val resStorage: ResStorage
) {

  var density = 1f
  var fontDensity = 1f
  private val displaySize = Point()
  private val displayMetrics = DisplayMetrics()

  fun init(context: Context) {
    setResStorageAppContext(context)
    onConfigChanged(context, null)
  }

  fun onActivityContextAvailable(context: Context) {
    resStorage.setContext(context)
  }

  fun onActivityContextUnAvailable() {
    setResStorageAppContext(App.instance)
  }

  private fun setResStorageAppContext(context: Context) {
    resStorage.setContext(context)
  }

  fun onConfigChanged(context: Context, newConfiguration: Configuration?) {
    val configuration = newConfiguration ?: context.resources.configuration

    density = context.resources.displayMetrics.density
    fontDensity = context.resources.displayMetrics.scaledDensity

    val manager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    manager?.let {
      val display = manager.defaultDisplay
      if (display != null) {
        display.getMetrics(displayMetrics)
        display.getSize(displaySize)
      }
    }

    if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
      val newSize = ceil(configuration.screenWidthDp * density).toInt()
      if (abs(displaySize.x - newSize) > 3) {
        displaySize.x = newSize
      }
    }
    if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
      val newSize = ceil(configuration.screenHeightDp * density).toInt()
      if (abs(displaySize.y - newSize) > 3) {
        displaySize.y = newSize
      }
    }

  }
}