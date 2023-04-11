package com.test.project.features.links

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.project.R
import com.test.project.features.links.models.HeaderModel
import com.test.project.features.links.models.LinkModel
import com.test.project.features.links.models.StationModel
import com.test.project.shared.compose.theme.AppTheme
import com.test.project.shared.compose.theme.PrimaryGreen
import com.test.project.shared.compose.widgets.AppBar
import com.test.project.shared.compose.widgets.EmptyViewUI
import com.test.project.shared.compose.widgets.LoadingUI
import com.test.project.shared.compose.widgets.SetSystemUiPrimarySurfaceColor
import com.test.project.shared.fragments.HandleOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinkFragment : Fragment(), HandleOnBackPressed {

  private val viewModel: LinkVM by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setUrl(navArgs<LinkFragmentArgs>().value.url)
    viewModel.loadLinks()
  }

  override fun handleOnBackPressed(): Boolean = viewModel.onBackPressed()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(inflater.context).apply {
      setContent {
        AppTheme {
          SetSystemUiPrimarySurfaceColor()
          Scaffold(
            modifier = Modifier
              .statusBarsPadding()
              .navigationBarsPadding(),
            topBar = {
              val title by viewModel.title.collectAsState()
              val showBackButton by viewModel.showBackButton.collectAsState()
              AppBar(
                title = title,
                showNavigationBackButton = showBackButton,
                onNavigationBackButtonClick = viewModel::onBackPressed
              )
            }
          ) { paddingValues ->
            val errorState by viewModel.errorState.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            AnimatedVisibility(
              modifier = Modifier
                .padding(
                  bottom = paddingValues.calculateBottomPadding()
                ),
              enter = fadeIn(),
              exit = fadeOut(),
              visible = !isLoading && !errorState.showNetworkErrorMessage && !errorState.showUnknownErrorMessage
            ) {
              ContentUI()
            }

            LoadingUI(isLoading)

            AnimatedVisibility(
              modifier = Modifier
                .padding(
                  bottom = paddingValues.calculateBottomPadding()
                ),
              enter = fadeIn(),
              exit = fadeOut(),
              visible = errorState.showNetworkErrorMessage || errorState.showUnknownErrorMessage
            ) {
              EmptyViewUI(
                onActionClick = viewModel::onRetry,
                showNetworkError = errorState.showNetworkErrorMessage,
                showUnexpectedError = errorState.showUnknownErrorMessage,
              )
            }
          }
        }
      }
    }
  }

  @Composable
  private fun ContentUI() {
    val items by viewModel.items.collectAsState()
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
    ) {
      items(items, key = { it.id() }) { item ->
        when (item) {
          is LinkModel -> {
            Text(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.onLinkClick(item) }
                .padding(24.dp),
              text = item.text,
              color = colorResource(id = R.color.primary_text)
            )
          }
          is HeaderModel -> {
            Text(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              text = item.text,
              color = PrimaryGreen,
              style = MaterialTheme.typography.subtitle1
            )
          }
          is StationModel -> {
            StationUI(item)
          }
        }
      }
    }
  }

  @Composable
  private fun StationUI(item: StationModel) {
    Row(
      modifier = Modifier
        .clickable { viewModel.onLinkClick(item) }
        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
      horizontalArrangement = Arrangement.Start
    ) {
      if (item.image.isNotEmpty()) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(item.image)
            .crossfade(true)
            .build(),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(48.dp)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
        )
      }

      Column(
        modifier = Modifier
          .padding(start = 16.dp)
          .weight(1f)
      ) {
        Text(
          modifier = Modifier
            .padding(bottom = 2.dp),
          text = item.text,
          color = colorResource(id = R.color.primary_text),
          style = MaterialTheme.typography.subtitle2
        )

        if (item.subtext.isNotEmpty()) {
          Text(
            text = item.subtext,
            color = colorResource(id = R.color.secondary_text),
            style = MaterialTheme.typography.body1
          )
        }
      }
    }
  }
}
