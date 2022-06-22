package com.test.project.features.settings.restaurants.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantsEntity(
  @Json(name = "total") val total: Int,
  @Json(name = "next") val next: Boolean,
  @Json(name = "data") val data: List<RestaurantEntity>
)