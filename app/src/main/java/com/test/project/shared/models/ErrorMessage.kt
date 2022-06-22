package com.test.project.shared.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorMessage(
  @Json(name = "code") val errorCode: String? = null,
  @Json(name = "detail") val errorDescription: String
)

fun ErrorMessage.toText() = "${errorCode ?: ""}:$errorDescription"
