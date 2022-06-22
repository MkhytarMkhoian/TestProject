package com.test.project.features.settings.restaurants.models

import com.test.project.shared.models.FeedItem
import com.test.project.shared.state.ErrorState

data class RestaurantsState(
  override val showNoInternetConnectionDialog: Boolean = false,
  override val showServerErrorDialog: Boolean = false,
  override val showAlertDialogLeftButton: Boolean = false,
  override val showAlertDialogRightButton: Boolean = true,
  override val actionId: String = "",
  val isDialogLoading: Boolean = false,
  val isLoading: Boolean = false,
  val items: List<FeedItem> = emptyList(),
) : ErrorState
