package com.test.project.shared.api

import com.test.project.features.links.models.LinksResponse
import com.test.project.features.player.models.StationResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface AppApiService {

  @GET
  suspend fun getLinks(@Url url: String, @Query("render") render: String): LinksResponse

  @GET
  suspend fun getStation(@Url url: String, @Query("render") render: String): StationResponse
}
