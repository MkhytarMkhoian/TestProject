package com.test.project.features.links.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LinkType(val id: String) : Parcelable {
  @Parcelize
  object Link : LinkType(id = "link")

  @Parcelize
  object Audio : LinkType(id = "audio")

  @Parcelize
  object None : LinkType(id = "none")

  companion object {
    fun from(id: String?): LinkType {
      return when (id) {
        Link.id -> Link
        Audio.id -> Audio
        else -> None
      }
    }
  }
}