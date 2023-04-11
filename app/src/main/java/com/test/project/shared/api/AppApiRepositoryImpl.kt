package com.test.project.shared.api

import com.test.project.features.links.models.LinksResponse
import com.test.project.features.player.models.StationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiRepositoryImpl @Inject constructor(
  private val errorMapper: ErrorMapper,
  private val service: AppApiService,
) : AppApiRepository {

  private suspend fun <T : Any> mapError(method: String, function: suspend () -> T): T {
    try {
      return function()
    } catch (t: Throwable) {
      throw errorMapper.mapError(t, method)
    }
  }

  override suspend fun getLinks(url: String): LinksResponse {
    return mapError("GET ".plus(url)) {
      service.getLinks(url, "json")
    }
  }

  override suspend fun getStation(url: String): StationResponse {
    return mapError("GET ".plus(url)) {
      service.getStation(url, "json")
    }
  }
}
