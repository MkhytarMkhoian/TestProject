package com.test.project.shared.storage

import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStorage
@Inject constructor(
  private val dataStorage: DataStorage
) {

  companion object {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN_KEY")
  }

  suspend fun setAccessToken(token: String) {
    dataStorage.put(ACCESS_TOKEN_KEY, token)
  }

  suspend fun getAccessToken(): String? {
    return dataStorage.get(ACCESS_TOKEN_KEY)
  }

  suspend fun setRefreshToken(token: String) {
    dataStorage.put(REFRESH_TOKEN_KEY, token)
  }

  suspend fun getRefreshToken(): String? {
    return dataStorage.get(REFRESH_TOKEN_KEY)
  }

  suspend fun clear() {
    dataStorage.remove(ACCESS_TOKEN_KEY)
    dataStorage.remove(REFRESH_TOKEN_KEY)
  }
}
