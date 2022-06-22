package com.test.project.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.project.R
import com.test.project.app.navigation.AppNavigator
import com.test.project.features.auth.login.interactors.LoginInteractor
import com.test.project.features.auth.login.models.LoginState
import com.test.project.features.settings.restaurants.interactors.RestaurantsInteractor
import com.test.project.features.settings.restaurants.models.RestaurantBundle
import com.test.project.shared.extensions.handleDefaultErrors
import com.test.project.shared.fragment.ErrorCallback
import com.test.project.shared.models.InputItem
import com.test.project.shared.models.KeyboardActionType
import com.test.project.shared.models.Result
import com.test.project.shared.resources.ResStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginVMContract {

  val state: StateFlow<LoginState>
}

@HiltViewModel
class LoginVM @Inject constructor(
  private val appNavigator: AppNavigator,
  private val reducer: LoginReducer,
  private val loginInteractor: LoginInteractor,
  private val restaurantsInteractor: RestaurantsInteractor,
  resStorage: ResStorage
) : ViewModel(), LoginVMContract, ErrorCallback {

  private val _state = MutableStateFlow(
    LoginState(
      email = InputItem(
        id = "Email",
        placeholder = resStorage.getString(R.string.login__email),
        subLabel = "",
        isError = false,
        value = "",
        maxLength = null,
        isRequired = false,
        keyboardActionType = KeyboardActionType.Next
      ),
      password = InputItem(
        id = "Password",
        placeholder = resStorage.getString(R.string.login__password),
        subLabel = "",
        isError = false,
        value = "",
        maxLength = null,
        isRequired = false,
        keyboardActionType = KeyboardActionType.Done
      )
    )
  )
  override val state: StateFlow<LoginState> = _state

  fun onEmailChanged(param: InputItem) {
    viewModelScope.launch {
      _state.value = reducer.emailReduce(_state.value, param)
    }
  }

  fun onPasswordChanged(param: InputItem) {
    viewModelScope.launch {
      _state.value = reducer.passwordReduce(_state.value, param)
    }
  }

  fun onLogin() {
    viewModelScope.launch {
      _state.value = _state.value.copy(isLoading = true)
      val validateResult = loginInteractor.validate(_state.value)
      if (!validateResult.isInvalid) {
        when (val result = loginInteractor.login(
          validateResult.state.email.value,
          validateResult.state.password.value
        )) {
          is Result.Error -> {
            handleDefaultErrors(result)
          }
          is Result.Success -> {
            loadRestaurants()
          }
        }

        _state.value = _state.value.copy(isLoading = false)
      } else {
        _state.value = validateResult.state.copy(isLoading = false)
      }
    }
  }

  fun onHelp() {
    appNavigator.navigate(
      LoginFragmentDirections.navActionHelp()
    )
  }

  private suspend fun loadRestaurants() {
    when (val result = restaurantsInteractor.getRestaurants()) {
      is Result.Error -> {
        handleDefaultErrors(result)
      }
      is Result.Success -> {
        val size = result.data?.size ?: 0
        val first = result.data?.firstOrNull()
        if (size == 1 && first != null) {
          restaurantsInteractor.setRestaurant(first)
        } else {
          appNavigator.navigate(
            LoginFragmentDirections.navActionRestaurants(
              RestaurantBundle(
                isSettingMode = false,
                restaurants = result.data ?: emptyList()
              )
            )
          )
        }
      }
    }
  }

  private fun handleDefaultErrors(result: Result.Error) {
    handleDefaultErrors(
      result,
      onNoInternetConnection = {
        _state.value =
          _state.value.copy(isLoading = false, showNoInternetConnectionDialog = true)
      },
      onServerError = {
        _state.value = _state.value.copy(isLoading = false, showServerErrorDialog = true)
      },
      onGenericError = {
        _state.value = _state.value.copy(isLoading = false, showServerErrorDialog = true)
      },
      onRequestError = { error ->
        _state.value = _state.value.copy(
          wasErrorFromBackend = true,
          isLoading = false,
          email = _state.value.email.copy(isError = true),
          password = _state.value.password.copy(
            isError = true,
            subLabel = error.errorMessage.errorDescription
          )
        )
      },
    )
  }

  private fun onCancelAlertDialog() {
    _state.value =
      _state.value.copy(showNoInternetConnectionDialog = false, showServerErrorDialog = false)
  }

  override val onNoInternetConnectionLeftButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
  }

  override val onNoInternetConnectionRightButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
    onLogin()
  }

  override val onServerErrorLeftButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
  }

  override val onServerErrorRightButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
    onLogin()
  }
}
