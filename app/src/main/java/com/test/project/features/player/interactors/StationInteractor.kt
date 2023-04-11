package com.test.project.features.player.interactors

import com.test.project.shared.api.AppApiRepository
import com.test.project.shared.api.UrlManager
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.interactors.Interactor
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StationInteractor @Inject constructor(
  private val appApiRepository: AppApiRepository,
  private val coroutineDispatchers: CoroutineDispatchers,
  private val urlManager: UrlManager,
) : Interactor {

  suspend fun getStationLink(url: String) =
    withContext(coroutineDispatchers.defaultContext) {
      val response = appApiRepository.getStation(url)
      response.body.firstOrNull()?.run {
        this.url?.run {
          urlManager.fixHttpsIfNeeded(this)
        }
      }
    }
}

