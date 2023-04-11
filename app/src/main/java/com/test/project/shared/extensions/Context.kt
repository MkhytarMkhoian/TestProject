package com.test.project.shared.extensions

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

@ColorInt
fun Context.getColorCompat(id: Int): Int {
  return ContextCompat.getColor(this, id)
}