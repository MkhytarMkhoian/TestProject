package com.test.project.shared.api

import com.test.project.features.links.models.LinksResponse
import com.test.project.features.player.models.StationResponse
import com.test.project.shared.models.HeadEntity

class AppApiRepositoryMock : AppApiRepository {

  override suspend fun getLinks(url: String): LinksResponse {
    return LinksResponse(head = HeadEntity("", ""), body = emptyList())
  }

  override suspend fun getStation(url: String): StationResponse {
    return StationResponse(head = HeadEntity("", ""), body = emptyList())
  }
}
