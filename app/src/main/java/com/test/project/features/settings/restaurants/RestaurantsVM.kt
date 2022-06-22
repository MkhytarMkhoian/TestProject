package com.test.project.features.settings.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.project.app.navigation.AppNavigator
import com.test.project.features.settings.restaurants.interactors.RestaurantsInteractor
import com.test.project.features.settings.restaurants.models.RestaurantBundle
import com.test.project.features.settings.restaurants.models.RestaurantModel
import com.test.project.features.settings.restaurants.models.RestaurantsState
import com.test.project.shared.extensions.handleDefaultErrors
import com.test.project.shared.fragment.ErrorCallback
import com.test.project.shared.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RestaurantsVMContract {

  val state: StateFlow<RestaurantsState>
}

@HiltViewModel
class RestaurantsVM @Inject constructor(
  private val reducer: RestaurantsReducer,
  private val restaurantsInteractor: RestaurantsInteractor,
  private val appNavigator: AppNavigator,
) : ViewModel(), RestaurantsVMContract, ErrorCallback {

  private val _state = MutableStateFlow(RestaurantsState())
  override val state: StateFlow<RestaurantsState> = _state

  private lateinit var bundle: RestaurantBundle

  fun setBundle(bundle: RestaurantBundle) {
    this.bundle = bundle

    if (bundle.isSettingMode) {
      loadRestaurants()
    } else {
      _state.value = _state.value.copy(items = bundle.restaurants)
    }
  }

  private fun loadRestaurants() {
    viewModelScope.launch {
      _state.value = _state.value.copy(isLoading = true)
      when (val result = restaurantsInteractor.getRestaurants()) {
        is Result.Error -> {
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
          )
        }
        is Result.Success -> {
          _state.value = _state.value.copy(isLoading = false, items = result.data ?: emptyList())
        }
      }
    }
  }

  fun onRestaurantClick(item: RestaurantModel) {
    viewModelScope.launch {
      _state.value = _state.value.copy(isDialogLoading = true)
      _state.value = reducer.reduceRestaurant(_state.value, item)
      restaurantsInteractor.setRestaurant(item)
      _state.value = _state.value.copy(isDialogLoading = false)
    }
  }

  fun onCloseClick() {
    appNavigator.popBackStack()
  }

  private fun onCancelAlertDialog() {
    _state.value = _state.value.copy(showNoInternetConnectionDialog = false, showServerErrorDialog = false)
  }

  override val onNoInternetConnectionLeftButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
  }

  override val onNoInternetConnectionRightButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
    loadRestaurants()
  }

  override val onServerErrorLeftButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
  }

  override val onServerErrorRightButtonClick: (String) -> Unit = {
    onCancelAlertDialog()
    loadRestaurants()
  }
}
