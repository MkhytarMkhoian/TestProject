package com.test.project.shared.extensions

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

/**
 * Navigate avoiding crash when nav controller has already changed current destination
 *
 * >IllegalArgumentException: Navigation action/destination cannot be found from the current destination
 *
 * [https://allsetnow.atlassian.net/browse/SWAD-1332]
 */
fun NavController.navigateSafe(
  resId: Int, arguments: Bundle? = null, navOptions: NavOptions? = null
) {
  try {
    navigate(resId, arguments, navOptions)
  } catch (e: IllegalArgumentException) {
    // ignore ?
  }
}

/**
 * Navigate avoiding crash when nav controller has already changed current destination
 *
 * >IllegalArgumentException: Navigation action/destination cannot be found from the current destination
 *
 * [https://allsetnow.atlassian.net/browse/SWAD-1332]
 */
fun NavController.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
  navigateSafe(directions.actionId, directions.arguments, navOptions)
}

fun NavController.navigateSafe(deeplink: Uri) {
  try {
    navigate(deeplink)
  } catch (e: IllegalArgumentException) {
    // ignore ?
  }
}
