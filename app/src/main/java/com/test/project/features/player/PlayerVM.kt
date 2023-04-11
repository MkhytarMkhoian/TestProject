package com.test.project.features.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.test.project.app.navigation.AppNavigator
import com.test.project.features.player.interactors.PlayerInteractor
import com.test.project.features.player.interactors.StationInteractor
import com.test.project.features.player.models.PlayerBundle
import com.test.project.features.player.models.PlayerUiState
import com.test.project.shared.api.ex.BaseApiException
import com.test.project.shared.api.ex.NetworkException
import com.test.project.shared.extensions.exceptionsHandler
import com.test.project.shared.state.ErrorUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class PlayerVM @Inject constructor(
  private val appNavigator: AppNavigator,
  private val stationInteractor: StationInteractor,
  private val playerInteractor: PlayerInteractor,
) : ViewModel() {

  private lateinit var bundle: PlayerBundle

  private val _uiState = MutableStateFlow(PlayerUiState())
  val uiState: StateFlow<PlayerUiState> = _uiState

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  private val _errorState = MutableStateFlow(ErrorUIState())
  val errorState: StateFlow<ErrorUIState> = _errorState

  private val _isPlaying = MutableStateFlow(false)
  val isPlaying: StateFlow<Boolean> = _isPlaying

  fun setBundle(bundle: PlayerBundle) {
    this.bundle = bundle
  }

  fun loadStation() {
    viewModelScope.launch {
      exceptionsHandler(
        {
          _errorState.value = _errorState.value.copy(
            showNetworkErrorMessage = false,
            showUnknownErrorMessage = false
          )
          _isLoading.value = true
          val stationLink = stationInteractor.getStationLink(bundle.model.url)
          _isLoading.value = false
          _uiState.value = PlayerUiState(
            title = bundle.model.text,
            podcastName = bundle.model.subtext,
            podcastImageUrl = bundle.model.image
          )

          stationLink?.let {
            playerInteractor.setMediaItem(stationLink)
          }
        },
        { e ->
          _isLoading.value = false
          if (e is NetworkException) {
            _errorState.value = _errorState.value.copy(
              showNetworkErrorMessage = true,
              showUnknownErrorMessage = false
            )

          } else if (e is BaseApiException) {
            _errorState.value = _errorState.value.copy(
              showNetworkErrorMessage = false,
              showUnknownErrorMessage = true
            )
          }
          // TODO report to crashlytics
        }
      )

    }
  }

  fun onPlay() {
    if (isPlaying.value) {
      playerInteractor.pause()
      _isPlaying.value = false
    } else {
      playerInteractor.play()
      _isPlaying.value = true
    }
  }

  fun onRetry() {
    loadStation()
  }

  fun onStart() {
    playerInteractor.onStart()
  }

  fun onResume() {
    playerInteractor.onResume()
  }

  fun onPause() {
    playerInteractor.onPause()
    _isPlaying.value = false
  }

  fun onStop() {
    playerInteractor.onStop()
    _isPlaying.value = false
  }

  fun onBackPressed(): Boolean {
    return appNavigator.popBackStack()
  }
}
