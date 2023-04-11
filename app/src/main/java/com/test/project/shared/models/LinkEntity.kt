package com.test.project.shared.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkEntity(
  @Json(name = "element") val element: String,
  @Json(name = "type") val type: String? = null,
  @Json(name = "text") val text: String? = null,
  @Json(name = "URL") val url: String? = null,
  @Json(name = "key") val key: String? = null,
  @Json(name = "children") val children: List<StationEntity>? = null,
)

@JsonClass(generateAdapter = true)
data class StationEntity(
  @Json(name = "element") val element: String,
  @Json(name = "type") val type: String,
  @Json(name = "text") val text: String,
  @Json(name = "URL") val url: String,
  @Json(name = "bitrate") val bitrate: String? = null,
  @Json(name = "reliability") val reliability: String? = null,
  @Json(name = "guide_id") val guideId: String? = null,
  @Json(name = "subtext") val subtext: String? = null,
  @Json(name = "genre_id") val genreId: String? = null,
  @Json(name = "formats") val formats: String? = null,
  @Json(name = "show_id") val showId: String? = null,
  @Json(name = "item") val item: String? = null,
  @Json(name = "image") val image: String? = null,
  @Json(name = "current_track") val currentTrack: String? = null,
  @Json(name = "now_playing_id") val nowPlayingId: String? = null,
  @Json(name = "preset_id") val presetId: String? = null,
)