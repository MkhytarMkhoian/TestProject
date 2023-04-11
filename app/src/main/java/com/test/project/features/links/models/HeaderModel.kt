package com.test.project.features.links.models

import com.test.project.shared.models.FeedItem

data class HeaderModel(
  val id: Long,
  val text: String,
) : FeedItem {
  override fun id(): Long = id
}