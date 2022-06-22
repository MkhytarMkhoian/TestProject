package com.test.project.app.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.test.project.shared.extensions.navigateSafe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator {

  private var navController: NavController? = null

  override fun navigate(directions: NavDirections, navOptions: NavOptions?) {
    navController?.navigateSafe(directions, navOptions)
  }

  override fun navigate(resId: Int, arguments: Bundle?, navOptions: NavOptions?) {
    navController?.navigateSafe(resId, arguments, navOptions)
  }

  override fun popBackStack(): Boolean {
    return navController?.popBackStack() ?: false
  }

  override fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean {
    return navController?.popBackStack(destinationId, inclusive) ?: false
  }

  override fun register(navController: NavController?) {
    this.navController = navController
  }

  override fun unregister() {
    navController = null
  }
}