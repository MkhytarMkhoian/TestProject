package com.test.project.shared.api

import com.test.project.features.settings.restaurants.models.RestaurantsEntity
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {

  @GET("application/v1/restaurants/")
  suspend fun getRestaurants(): Response<RestaurantsEntity>
}
