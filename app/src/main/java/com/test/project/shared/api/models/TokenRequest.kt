package com.test.project.shared.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenRequest(
  @Json(name = "email") val email: String,
  @Json(name = "password") val password: String
)
