package com.test.project.features.settings.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.test.project.R
import com.test.project.features.settings.restaurants.models.RestaurantModel
import com.test.project.features.settings.restaurants.models.RestaurantsState
import com.test.project.shared.compose.theme.AppTheme
import com.test.project.shared.compose.widgets.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsFragment : Fragment() {

  private val restaurantsVM: RestaurantsVM by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    restaurantsVM.setBundle(navArgs<RestaurantsFragmentArgs>().value.bundle)
  }

  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(inflater.context).apply {
      setContent {
        AppTheme {
          Scaffold(
            modifier = Modifier
              .statusBarsPadding()
              .navigationBarsPadding(),
            topBar = {
              AppBar(
                navigationBackButtonIcon = painterResource(id = R.drawable.ic_back),
                onNavigationBackButtonClick = { restaurantsVM.onCloseClick() },
                title = "test",
                actions = {
                  ToolbarIconButton(
                    onClick = { restaurantsVM.onCloseClick() },
                    painter = painterResource(id = R.drawable.ic_button_close_black),
                    contentPadding = PaddingValues(
                      start = 8.dp,
                      top = 0.dp,
                      end = 20.dp,
                      bottom = 0.dp
                    )
                  )
                },
              )
            }
          ) { paddingValues ->
            val state by restaurantsVM.state.collectAsState()
            val windowSizeClass = calculateWindowSizeClass(requireActivity())

            AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = !state.isLoading) {
              ContentUI(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                isWindowCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
                state = state
              )
            }

            CircularProgressIndicatorUI(state.isLoading)

            if (state.isDialogLoading) {
              ProgressDialog()
            }

            HandleDefaultErrors(
              state = state,
              callback = restaurantsVM
            )
          }
        }
      }
    }
  }

  @Composable
  private fun ContentUI(
    modifier: Modifier,
    isWindowCompact: Boolean,
    state: RestaurantsState
  ) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .navigationBarsPadding(),
    ) {
      LazyColumn(
        modifier = Modifier
          .widthIn(max = 550.dp)
          .fillMaxHeight()
          .align(Alignment.TopCenter)
          .imePadding(),
      ) {
        itemsIndexed(state.items) { index, item ->
          when (item) {
            is RestaurantModel -> {
              val shape = if (isWindowCompact) {
                RectangleShape
              } else {
                when (index) {
                  0 -> {
                    MaterialTheme.shapes.small.copy(
                      bottomEnd = ZeroCornerSize,
                      bottomStart = ZeroCornerSize
                    )
                  }
                  state.items.size - 1 -> {
                    MaterialTheme.shapes.small.copy(
                      topEnd = ZeroCornerSize,
                      topStart = ZeroCornerSize
                    )
                  }
                  else -> RectangleShape
                }
              }

              RestaurantItemUI(
                modifier = Modifier
                  .padding(bottom = 1.dp)
                  .background(
                    color = MaterialTheme.colors.surface,
                    shape = shape
                  )
                  .fillMaxWidth()
                  .defaultMinSize(minHeight = 104.dp)
                  .clickable { restaurantsVM.onRestaurantClick(item) }
                  .padding(start = 24.dp, end = 24.dp),
                item = item,
              )
            }
          }
        }
      }
    }
  }

  @Composable
  fun RestaurantItemUI(
    modifier: Modifier = Modifier,
    item: RestaurantModel,
  ) {
    Row(
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        if (item.title.isNotEmpty()) {
          Text(
            text = item.title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface,
          )
        }

        if (item.address.isNotEmpty()) {
          Text(
            text = item.address,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colors.onPrimary,
          )
        }
      }

      if (item.isSelected) {
        Spacer(modifier = Modifier.size(16.dp))
        Image(
          painter = painterResource(id = R.drawable.ic_check),
          contentDescription = "check",
        )
      }
    }
  }
}
