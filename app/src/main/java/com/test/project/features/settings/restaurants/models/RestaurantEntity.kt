package com.test.project.features.settings.restaurants.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantEntity(
  @Json(name = "id") val id: Int,
  @Json(name = "title") val title: String,
  @Json(name = "address") val address: String,
  @Json(name = "tz_offset") val tzOffset: String,
)