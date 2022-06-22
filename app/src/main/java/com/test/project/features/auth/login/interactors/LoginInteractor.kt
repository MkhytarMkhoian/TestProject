package com.test.project.features.auth.login.interactors

import com.test.project.features.auth.interactors.RefreshTokenInteractor
import com.test.project.features.auth.login.models.LoginState
import com.test.project.features.auth.login.models.LoginValidationResult
import com.test.project.shared.api.models.TokenResponse
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.interactors.Interactor
import com.test.project.shared.models.Result
import com.test.project.shared.validators.EmailValidator
import com.test.project.shared.validators.PasswordValidator
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginInteractor @Inject constructor(
  private val tokenInteractor: RefreshTokenInteractor,
  private val coroutineDispatchers: CoroutineDispatchers,
  private val passwordValidator: PasswordValidator,
  private val emailValidator: EmailValidator,
) : Interactor {

  suspend fun login(email: String, password: String): Result<TokenResponse> {
    return tokenInteractor.getToken(
      email = email,
      password = password
    )
  }

  suspend fun validate(state: LoginState): LoginValidationResult {
    return withContext(coroutineDispatchers.defaultContext) {
      val emailValidationResult = emailValidator.validate(state.email.value)
      val passwordValidationResult = passwordValidator.validate(state.password.value)

      return@withContext LoginValidationResult(
        isInvalid = emailValidationResult.isInvalid() || passwordValidationResult.isInvalid(),
        state = state.copy(
          email = state.email.copy(
            subLabel = if (emailValidationResult.isInvalid()) {
              emailValidationResult.message
            } else "",
            isError = emailValidationResult.isInvalid()
          ),
          password = state.password.copy(
            subLabel = if (passwordValidationResult.isInvalid()) {
              passwordValidationResult.message
            } else "",
            isError = passwordValidationResult.isInvalid()
          )
        )
      )
    }
  }
}

