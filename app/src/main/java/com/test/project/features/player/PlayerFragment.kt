package com.test.project.features.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.PauseCircleFilled
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.util.UnstableApi
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.project.R
import com.test.project.features.player.models.PlayerUiState
import com.test.project.features.player.util.DynamicThemePrimaryColorsFromImage
import com.test.project.features.player.util.contrastAgainst
import com.test.project.features.player.util.rememberDominantColorState
import com.test.project.features.player.util.verticalGradientScrim
import com.test.project.shared.compose.theme.AppTheme
import com.test.project.shared.compose.theme.MinContrastOfPrimaryVsSurface
import com.test.project.shared.compose.widgets.EmptyViewUI
import com.test.project.shared.compose.widgets.LoadingUI
import com.test.project.shared.fragments.HandleOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@UnstableApi
@AndroidEntryPoint
class PlayerFragment : Fragment(), HandleOnBackPressed {

  private val viewModel: PlayerVM by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setBundle(navArgs<PlayerFragmentArgs>().value.bundle)
    viewModel.loadStation()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
  }

  override fun onResume() {
    super.onResume()
    viewModel.onResume()
  }

  override fun onPause() {
    super.onPause()
    viewModel.onPause()
  }

  override fun onStop() {
    super.onStop()
    viewModel.onStop()
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
          val errorState by viewModel.errorState.collectAsState()
          val isLoading by viewModel.isLoading.collectAsState()

          AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = !isLoading && !errorState.showNetworkErrorMessage && !errorState.showUnknownErrorMessage
          ) {
            val uiState by viewModel.uiState.collectAsState()

            // TODO Add layouts for landscape orientation
            PlayerContent(uiState = uiState)
          }

          LoadingUI(isLoading)

          AnimatedVisibility(
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

  @Composable
  fun PlayerContent(
    modifier: Modifier = Modifier,
    uiState: PlayerUiState,
  ) {
    PlayerDynamicTheme(uiState.podcastImageUrl) {
      PlayerContentRegular(uiState, modifier)
    }
  }

  /**
   * The UI for the top pane of a tabletop layout.
   */
  @Composable
  private fun PlayerContentRegular(
    uiState: PlayerUiState,
    modifier: Modifier = Modifier
  ) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .verticalGradientScrim(
          color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
          startYPercentage = 1f,
          endYPercentage = 0f
        )
        .systemBarsPadding()
        .padding(horizontal = 8.dp)
    ) {
      TopAppBar()
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
      ) {
        Spacer(modifier = Modifier.weight(1f))
        PlayerImage(
          podcastImageUrl = uiState.podcastImageUrl,
          modifier = Modifier.weight(10f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        PodcastDescription(uiState.title, uiState.podcastName)
        Spacer(modifier = Modifier.height(32.dp))
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.weight(10f)
        ) {
          PlayerSlider()
          PlayerButtons(Modifier.padding(vertical = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }

  @Composable
  private fun TopAppBar() {
    Row(Modifier.fillMaxWidth()) {
      IconButton(onClick = viewModel::onBackPressed) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = stringResource(R.string.cd_back)
        )
      }
      Spacer(Modifier.weight(1f))
      IconButton(onClick = { /* TODO */ }) {
        Icon(
          imageVector = Icons.Default.PlaylistAdd,
          contentDescription = stringResource(R.string.cd_add)
        )
      }
      IconButton(onClick = { /* TODO */ }) {
        Icon(
          imageVector = Icons.Default.MoreVert,
          contentDescription = stringResource(R.string.cd_more)
        )
      }
    }
  }

  @Composable
  private fun PlayerImage(
    podcastImageUrl: String,
    modifier: Modifier = Modifier
  ) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(podcastImageUrl)
        .crossfade(true)
        .build(),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = modifier
        .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
        .aspectRatio(1f)
        .clip(MaterialTheme.shapes.medium)
    )
  }

  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  private fun PodcastDescription(
    title: String,
    podcastName: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.h5
  ) {
    Text(
      text = title,
      style = titleTextStyle,
      maxLines = 1,
      modifier = Modifier.basicMarquee()
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
      Text(
        text = podcastName,
        style = MaterialTheme.typography.body2,
        maxLines = 1
      )
    }
  }

  @Composable
  private fun PlayerSlider() {
    Column(Modifier.fillMaxWidth()) {
      Slider(value = 0f, onValueChange = { })
      Row(Modifier.fillMaxWidth()) {
        Text(text = "0s")
        Spacer(modifier = Modifier.weight(1f))
      }
    }
  }

  // TODO Connect buttons to it's features
  @Composable
  private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp
  ) {
    Row(
      modifier = modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      val buttonsModifier = Modifier
        .size(sideButtonSize)
        .semantics { role = Role.Button }

      Image(
        imageVector = Icons.Filled.SkipPrevious,
        contentDescription = stringResource(R.string.cd_skip_previous),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = buttonsModifier
      )
      Image(
        imageVector = Icons.Filled.Replay10,
        contentDescription = stringResource(R.string.cd_reply10),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = buttonsModifier
      )

      val isPlaying by viewModel.isPlaying.collectAsState()
      Image(
        imageVector = if (isPlaying) {
          Icons.Rounded.PauseCircleFilled
        } else {
          Icons.Rounded.PlayCircleFilled
        },
        contentDescription = stringResource(R.string.cd_play),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = Modifier
          .size(playerButtonSize)
          .semantics { role = Role.Button }
          .clickable(onClick = viewModel::onPlay),
      )
      Image(
        imageVector = Icons.Filled.Forward30,
        contentDescription = stringResource(R.string.cd_forward30),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = buttonsModifier
      )
      Image(
        imageVector = Icons.Filled.SkipNext,
        contentDescription = stringResource(R.string.cd_skip_next),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = buttonsModifier
      )
    }
  }

  /**
   * Theme that updates the colors dynamically depending on the podcast image URL
   */
  @Composable
  private fun PlayerDynamicTheme(
    podcastImageUrl: String,
    content: @Composable () -> Unit
  ) {
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState(
      defaultColor = MaterialTheme.colors.surface
    ) { color ->
      // We want a color which has sufficient contrast against the surface color
      color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
      // Update the dominantColorState with colors coming from the podcast image URL
      LaunchedEffect(podcastImageUrl) {
        if (podcastImageUrl.isNotEmpty()) {
          dominantColorState.updateColorsFromImageUrl(podcastImageUrl)
        } else {
          dominantColorState.reset()
        }
      }
      content()
    }
  }
}
