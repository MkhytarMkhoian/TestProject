package com.test.project.features.auth.interactors

import com.test.project.features.auth.repositories.TokenRepository
import com.test.project.shared.api.models.TokenRequest
import com.test.project.shared.api.models.TokenResponse
import com.test.project.shared.interactors.Interactor
import com.test.project.shared.models.Result
import com.test.project.shared.storage.TokenDataStorage
import javax.inject.Inject

interface RefreshTokenInteractor : Interactor {
  suspend fun refreshToken(): Result<String>
  suspend fun getToken(email: String, password: String): Result<TokenResponse>
  suspend fun blacklistToken()
}

class RefreshTokenInteractorImpl @Inject constructor(
  private val tokenRepository: TokenRepository,
  private val dataStorage: TokenDataStorage
) : RefreshTokenInteractor {

  override suspend fun refreshToken(): Result<String> {
    val refreshToken = dataStorage.getRefreshToken()
    return if (refreshToken.isNullOrEmpty()) {
      Result.Success("")
    } else {
      val result = tokenRepository.refreshToken(refreshToken)
      if (result is Result.Success) {
        val token = result.data!!.access
        dataStorage.setAccessToken(result.data.access)
        Result.Success(token)
      } else Result.Success("")
    }
  }

  override suspend fun getToken(email: String, password: String): Result<TokenResponse> {
    val result = tokenRepository.getToken(TokenRequest(email, password))
    return if (result is Result.Success) {
      result.data?.let { data ->
        dataStorage.setAccessToken(data.access)
        dataStorage.setRefreshToken(data.refresh)
      }
      result
    } else result
  }

  override suspend fun blacklistToken() {
    val refreshToken = dataStorage.getRefreshToken()
    refreshToken?.run { tokenRepository.blacklistToken(refreshToken) }

    dataStorage.clear()
  }
}
