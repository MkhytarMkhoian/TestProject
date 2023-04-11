package com.test.project.shared.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeadEntity(
  @Json(name = "title") val title: String? = null,
  @Json(name = "status") val status: String
)