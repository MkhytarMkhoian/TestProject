package com.test.project.shared.resources

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.test.project.shared.extensions.getColorCompat
import javax.inject.Inject

class ResStorageImpl @Inject constructor() : ResStorage {

  private lateinit var context: Context

  override fun getStringArray(@ArrayRes resId: Int): List<String> {
    return context.resources.getStringArray(resId).toList()
  }

  override fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
    return try {
      context.getString(resId, *formatArgs)
    } catch (e: Exception) {
      getString(resId)
    }
  }

  override fun getString(@StringRes resId: Int): String {
    return context.getString(resId)
  }

  override fun getColor(resId: Int): Int {
    return context.getColorCompat(resId)
  }

  override fun setContext(context: Context) {
    this.context = context
  }
}
