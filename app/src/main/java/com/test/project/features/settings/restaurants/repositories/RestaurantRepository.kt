package com.test.project.features.settings.restaurants.repositories

import com.test.project.features.settings.restaurants.models.RestaurantsEntity
import com.test.project.shared.api.AppApi
import com.test.project.shared.api.network.ErrorParser
import com.test.project.shared.api.network.NetworkManager
import com.test.project.shared.api.network.SimpleRequestHandler
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.crashlytics.CrashReporting
import com.test.project.shared.models.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface RestaurantRepository {
  suspend fun getRestaurants(): Result<RestaurantsEntity>
}

@Singleton
class RestaurantRepositoryImpl @Inject constructor(
  private val crashReporting: CrashReporting,
  private val dispatchers: CoroutineDispatchers,
  private val networkManager: NetworkManager,
  private val errorParser: ErrorParser,
  private val api: AppApi,
) : RestaurantRepository {

  override suspend fun getRestaurants(): Result<RestaurantsEntity> =
    withContext(dispatchers.ioContext) {
      SimpleRequestHandler(
        crashReporting = crashReporting,
        networkManager = networkManager,
        errorParser = errorParser,
        onRequest = { api.getRestaurants() },
        onResult = { response ->
          Result.Success(response)
        }
      ).request()
    }
}
