package com.test.project.features.player.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.test.project.shared.models.HeadEntity

@JsonClass(generateAdapter = true)
data class StationResponse(
  @Json(name = "head") val head: HeadEntity,
  @Json(name = "body") val body: List<StationLinkEntity>,
)
