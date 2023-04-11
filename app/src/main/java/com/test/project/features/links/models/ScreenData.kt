package com.test.project.features.links.models

import com.test.project.shared.models.FeedItem

data class ScreenData(
  val title: String,
  val feedItems: List<FeedItem>,
)
