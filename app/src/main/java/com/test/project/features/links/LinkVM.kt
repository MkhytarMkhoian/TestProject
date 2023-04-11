package com.test.project.features.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.project.NavAppDirections
import com.test.project.app.navigation.AppNavigator
import com.test.project.features.links.interactors.LinkInteractor
import com.test.project.features.links.models.LinkModel
import com.test.project.features.links.models.StationModel
import com.test.project.features.player.models.PlayerBundle
import com.test.project.shared.api.ex.BaseApiException
import com.test.project.shared.api.ex.NetworkException
import com.test.project.shared.extensions.exceptionsHandler
import com.test.project.shared.models.FeedItem
import com.test.project.shared.state.ErrorUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkVM @Inject constructor(
  private val appNavigator: AppNavigator,
  private val linkInteractor: LinkInteractor,
) : ViewModel() {

  private var url: String? = null

  private val _title = MutableStateFlow("")
  val title: StateFlow<String> = _title

  private val _showBackButton = MutableStateFlow(false)
  val showBackButton: StateFlow<Boolean> = _showBackButton

  private val _items = MutableStateFlow<List<FeedItem>>(emptyList())
  val items: StateFlow<List<FeedItem>> = _items

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  private val _errorState = MutableStateFlow(ErrorUIState())
  val errorState: StateFlow<ErrorUIState> = _errorState

  fun setUrl(url: String?) {
    this.url = url
    _showBackButton.value = !url.isNullOrEmpty()
  }

  fun onRetry() {
    loadLinks()
  }

  fun loadLinks() {
    viewModelScope.launch {
      exceptionsHandler(
        {
          _errorState.value = _errorState.value.copy(
            showNetworkErrorMessage = false,
            showUnknownErrorMessage = false
          )
          _isLoading.value = true
          val data = linkInteractor.getLinks(url)
          _isLoading.value = false
          _title.value = data.title
          _items.value = data.feedItems
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

  fun onLinkClick(item: LinkModel) {
    appNavigator.navigate(
      NavAppDirections.navActionLinks(item.url)
    )
  }

  fun onLinkClick(item: StationModel) {
    appNavigator.navigate(
      LinkFragmentDirections.navActionPlayer(PlayerBundle(item))
    )
  }

  fun onBackPressed(): Boolean {
    return appNavigator.popBackStack()
  }
}
