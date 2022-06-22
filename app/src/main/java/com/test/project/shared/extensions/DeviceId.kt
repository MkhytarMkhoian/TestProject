package com.test.project.shared.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

@SuppressLint("HardwareIds")
fun getDeviceUID(context: Context): String {
  return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
