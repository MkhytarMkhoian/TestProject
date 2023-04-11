package com.test.project.features.player.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationLinkEntity(
  @Json(name = "url") val url: String? = null,
)