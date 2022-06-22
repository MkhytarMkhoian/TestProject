package com.test.project.shared.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenRequest(
  @Json(name = "refresh") val refresh: String,
)
