package com.test.project.shared.api

import com.test.project.shared.api.models.BlacklistTokenRequest
import com.test.project.shared.api.models.RefreshTokenRequest
import com.test.project.shared.api.models.RefreshTokenResponse
import com.test.project.shared.api.models.TokenRequest
import com.test.project.shared.api.models.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {

  @POST("authentication/v1/token/")
  suspend fun getToken(@Body request: TokenRequest): Response<TokenResponse>

  @POST("authentication/v1/token/refresh/")
  suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

  @POST("authentication/v1/token/blacklist/")
  suspend fun blacklistToken(@Body request: BlacklistTokenRequest): Response<Any>
}
