package com.test.project.features.links.models

import android.os.Parcelable
import com.test.project.shared.models.FeedItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class StationModel(
  val id: Long,
  val text: String,
  val subtext: String,
  val url: String,
  val image: String,
  val type: LinkType,
) : Parcelable, FeedItem {
  override fun id(): Long = id
}