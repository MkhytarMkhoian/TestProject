package com.test.project.app

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.soloader.SoLoader
import com.test.project.features.developer_settings.Flipper
import javax.inject.Inject

class FlipperInteractor @Inject constructor(
  private val flipper: Flipper,
) {

  fun setup(context: Context) {
    SoLoader.init(context, false)

    if (FlipperUtils.shouldEnableFlipper(context)) {
      val client = AndroidFlipperClient.getInstance(context)
      flipper.addNetworkFlipperPlugin(client)
      client.addPlugin(DatabasesFlipperPlugin(context))
      client.addPlugin(CrashReporterPlugin.getInstance())
      client.addPlugin(NavigationFlipperPlugin.getInstance())
      client.start()
    }
  }
}