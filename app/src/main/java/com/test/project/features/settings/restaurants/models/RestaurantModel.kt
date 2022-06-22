package com.test.project.features.settings.restaurants.models

import android.os.Parcelable
import com.test.project.shared.models.FeedItem
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class RestaurantModel(
  val id: Int,
  val title: String,
  val address: String,
  val isSelected: Boolean,
  val tzOffset: String
) : Parcelable, FeedItem, Serializable {
  override fun id(): Long = id.toLong()
}