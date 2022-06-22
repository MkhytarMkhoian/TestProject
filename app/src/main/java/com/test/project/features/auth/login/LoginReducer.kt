package com.test.project.features.auth.login

import com.test.project.features.auth.login.models.LoginState
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.models.InputItem
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginReducer @Inject constructor(
  private val coroutineDispatchers: CoroutineDispatchers,
) {

  suspend fun emailReduce(state: LoginState, param: InputItem): LoginState {
    return withContext(coroutineDispatchers.defaultContext) {
      return@withContext state.copy(
        email = param.copy(subLabel = "", isError = false),
        password = if (state.wasErrorFromBackend) state.password.copy(
          subLabel = "",
          isError = false
        ) else state.password,
        wasErrorFromBackend = false
      )
    }
  }

  suspend fun passwordReduce(state: LoginState, param: InputItem): LoginState {
    return withContext(coroutineDispatchers.defaultContext) {
      return@withContext state.copy(
        password = param.copy(subLabel = "", isError = false),
        email = if (state.wasErrorFromBackend) state.email.copy(
          subLabel = "",
          isError = false
        ) else state.email,
        wasErrorFromBackend = false
      )
    }
  }
}