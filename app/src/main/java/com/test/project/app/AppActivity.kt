package com.test.project.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.test.project.app.navigation.AppNavigator
import com.test.project.shared.activities.BaseActivity
import com.test.project.shared.fragments.HandleOnBackPressed
import com.test.project.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : BaseActivity() {

  @Inject
  lateinit var appNavigator: AppNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_app)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    val navController = navHostFragment.navController
    appNavigator.register(navController)

    onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        val handler =
          (navHostFragment.childFragmentManager.fragments.firstOrNull() as? HandleOnBackPressed)
        if (handler?.handleOnBackPressed() == false) {
          finish()
        }
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    appNavigator.unregister()
  }
}