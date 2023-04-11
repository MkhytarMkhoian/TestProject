package com.test.project.features.links.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.test.project.shared.models.HeadEntity
import com.test.project.shared.models.LinkEntity

@JsonClass(generateAdapter = true)
data class LinksResponse(
  @Json(name = "head") val head: HeadEntity,
  @Json(name = "body") val body: List<LinkEntity>,
)
