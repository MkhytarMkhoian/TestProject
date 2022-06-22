package com.test.project.features.auth.repositories

import com.test.project.shared.api.TokenApi
import com.test.project.shared.api.models.BlacklistTokenRequest
import com.test.project.shared.api.models.RefreshTokenRequest
import com.test.project.shared.api.models.RefreshTokenResponse
import com.test.project.shared.api.models.TokenRequest
import com.test.project.shared.api.models.TokenResponse
import com.test.project.shared.api.network.ErrorParser
import com.test.project.shared.api.network.NetworkManager
import com.test.project.shared.api.network.SimpleRequestHandler
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.crashlytics.CrashReporting
import com.test.project.shared.models.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface TokenRepository {
  suspend fun refreshToken(token: String): Result<RefreshTokenResponse>
  suspend fun getToken(request: TokenRequest): Result<TokenResponse>
  suspend fun blacklistToken(token: String): Result<Any>
}

@Singleton
class TokenRepositoryImpl @Inject constructor(
  private val crashReporting: CrashReporting,
  private val dispatchers: CoroutineDispatchers,
  private val networkManager: NetworkManager,
  private val errorParser: ErrorParser,
  private val api: TokenApi
) : TokenRepository {

  override suspend fun refreshToken(token: String): Result<RefreshTokenResponse> =
    withContext(dispatchers.ioContext) {
      SimpleRequestHandler(
        crashReporting = crashReporting,
        networkManager = networkManager,
        errorParser = errorParser,
        onRequest = { api.refreshToken(RefreshTokenRequest(token)) },
        onResult = { response ->
          Result.Success(response)
        }
      ).request()
    }

  override suspend fun getToken(request: TokenRequest): Result<TokenResponse> =
    withContext(dispatchers.ioContext) {
      SimpleRequestHandler(
        crashReporting = crashReporting,
        networkManager = networkManager,
        errorParser = errorParser,
        onRequest = { api.getToken(request) },
        onResult = { response ->
          Result.Success(response)
        }
      ).request()
    }

  override suspend fun blacklistToken(token: String): Result<Any> =
    withContext(dispatchers.ioContext) {
      SimpleRequestHandler(
        crashReporting = crashReporting,
        networkManager = networkManager,
        errorParser = errorParser,
        onRequest = { api.blacklistToken(BlacklistTokenRequest(token)) },
        onResult = { Result.Success(Any()) }
      )
    }.request()
}
