package com.test.project.features.developer_settings

import timber.log.Timber

class TaggableDebugTree(private val tagPrefix: String) : Timber.DebugTree() {

  override fun createStackElementTag(element: StackTraceElement): String {
    return tagPrefix + super.createStackElementTag(element)
  }

}
