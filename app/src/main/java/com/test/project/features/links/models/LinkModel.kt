package com.test.project.features.links.models

import com.test.project.shared.models.FeedItem

data class LinkModel(
  val id: Long,
  val element: String,
  val type: LinkType,
  val text: String,
  val url: String,
  val key: StationType?,
) : FeedItem {
  override fun id(): Long = id
}
