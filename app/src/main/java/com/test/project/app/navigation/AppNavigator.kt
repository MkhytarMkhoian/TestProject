package com.test.project.app.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

interface AppNavigator {

  fun register(navController: NavController?)
  fun unregister()

  fun navigate(resId: Int, arguments: Bundle? = null, navOptions: NavOptions? = null)
  fun navigate(directions: NavDirections, navOptions: NavOptions? = null)
  fun popBackStack(): Boolean
  fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean
}