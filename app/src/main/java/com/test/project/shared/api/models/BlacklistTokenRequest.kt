package com.test.project.shared.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlacklistTokenRequest(
  @Json(name = "refresh") val token: String
)
