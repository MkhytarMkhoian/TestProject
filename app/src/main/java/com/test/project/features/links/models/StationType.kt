package com.test.project.features.links.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class StationType(val id: String) : Parcelable {
  @Parcelize
  object Local : StationType(id = "local")

  @Parcelize
  object Music : StationType(id = "music")

  @Parcelize
  object Talk : StationType(id = "talk")

  @Parcelize
  object Sports : StationType(id = "sports")

  @Parcelize
  object Location : StationType(id = "location")

  @Parcelize
  object Language : StationType(id = "language")

  @Parcelize
  object Podcast : StationType(id = "podcast")

  @Parcelize
  object None : StationType(id = "none")

  companion object {
    fun from(id: String?): StationType {
      return when (id) {
        Local.id -> Local
        Music.id -> Music
        Talk.id -> Talk
        Sports.id -> Sports
        Location.id -> Location
        Language.id -> Language
        Podcast.id -> Podcast
        else -> None
      }
    }
  }
}