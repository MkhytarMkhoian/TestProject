package com.test.project.features.settings.restaurants.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantBundle(
  val restaurants: List<RestaurantModel>,
  val isSettingMode: Boolean,
) : Parcelable