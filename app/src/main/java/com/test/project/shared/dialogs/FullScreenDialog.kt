package com.test.project.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import com.test.project.R

open class FullScreenDialog : DialogFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)

    dialog.window?.run {
      setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
      WindowCompat.setDecorFitsSystemWindows(this, false)
    }

    return dialog
  }

}
