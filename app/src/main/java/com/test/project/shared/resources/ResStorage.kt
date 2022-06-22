package com.test.project.shared.resources

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResStorage {
    fun setContext(context: Context)

    fun getStringArray(@ArrayRes resId: Int): List<String>
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
    fun getString(@StringRes resId: Int): String
    fun getColor(@ColorRes resId: Int): Int
}