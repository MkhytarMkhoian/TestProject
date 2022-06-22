package com.test.project.shared.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.test.project.app.App

fun Context.toApp() = this.applicationContext as App

fun Context.performCopyToClipboard(value: String?) {
  val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clipData = ClipData.newPlainText("", value)
  clipboardManager.setPrimaryClip(clipData)
}

@ColorInt
fun Context.getColorCompat(id: Int): Int {
  return ContextCompat.getColor(this, id)
}

fun Context.canHandleIntent(intent: Intent) = packageManager.queryIntentActivities(intent, 0).isNotEmpty()
