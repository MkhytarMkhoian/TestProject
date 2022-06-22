package com.test.project.features.settings.restaurants

import com.test.project.features.settings.restaurants.models.RestaurantModel
import com.test.project.features.settings.restaurants.models.RestaurantsState
import com.test.project.shared.coroutines.CoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantsReducer @Inject constructor(
  private val coroutineDispatchers: CoroutineDispatchers,
) {

  suspend fun reduceRestaurant(state: RestaurantsState, item: RestaurantModel): RestaurantsState {
    return withContext(coroutineDispatchers.defaultContext) {
      val items = state.items.map {
        if (it is RestaurantModel && it.isSelected) {
          it.copy(isSelected = false)
        } else if (it.id() == item.id()) {
          item.copy(isSelected = true)
        } else it
      }

      return@withContext state.copy(items = items)
    }
  }
}