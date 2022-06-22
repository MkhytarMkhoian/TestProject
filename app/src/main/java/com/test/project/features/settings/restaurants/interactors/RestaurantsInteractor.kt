package com.test.project.features.settings.restaurants.interactors

import com.test.project.features.settings.restaurants.RestaurantsDataStorage
import com.test.project.features.settings.restaurants.models.RestaurantEntity
import com.test.project.features.settings.restaurants.models.RestaurantModel
import com.test.project.features.settings.restaurants.repositories.RestaurantRepository
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.interactors.Interactor
import com.test.project.shared.models.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantsInteractor @Inject constructor(
  private val restaurantsDataStorage: RestaurantsDataStorage,
  private val restaurantRepository: RestaurantRepository,
  private val coroutineDispatchers: CoroutineDispatchers,
) : Interactor {

  suspend fun setRestaurant(selectedRestaurant: RestaurantModel) =
    withContext(coroutineDispatchers.defaultContext) {
      return@withContext restaurantsDataStorage.setRestaurant(selectedRestaurant)
    }

  suspend fun getRestaurants(): Result<List<RestaurantModel>> =
    withContext(coroutineDispatchers.ioContext) {
      return@withContext when (val result = restaurantRepository.getRestaurants()) {
        is Result.Error -> result
        is Result.Success -> {
          val selectedRestaurant = restaurantsDataStorage.getRestaurant()
          Result.Success(
            mapRestaurants(
              items = result.data?.data ?: emptyList(),
              selectedRestaurant = selectedRestaurant
            )
          )
        }
      }
    }

  private fun mapRestaurants(
    items: List<RestaurantEntity>,
    selectedRestaurant: RestaurantModel?
  ): List<RestaurantModel> {
    return items.map { item ->
      RestaurantModel(
        id = item.id,
        title = item.title,
        address = item.address,
        isSelected = item.id == selectedRestaurant?.id,
        tzOffset = item.tzOffset
      )
    }
  }
}

