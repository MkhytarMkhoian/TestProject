package com.test.project.shared.api

import com.test.project.features.links.models.LinksResponse
import com.test.project.features.player.models.StationResponse

interface AppApiRepository {

  suspend fun getLinks(url: String): LinksResponse

  suspend fun getStation(url: String): StationResponse
}
