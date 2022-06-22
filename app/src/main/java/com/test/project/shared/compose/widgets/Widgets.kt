package com.test.project.shared.compose.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.test.project.R
import com.test.project.shared.fragment.ErrorCallback
import com.test.project.shared.state.ErrorState

const val DividerAlpha = 0.12f

@Composable
fun RadioButtonUI(
  modifier: Modifier = Modifier,
  name: String,
  isSelected: Boolean,
  onClick: (Boolean) -> Unit,
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier
        .defaultMinSize(minHeight = 58.dp)
        .clickable {
          onClick(if (!isSelected) true else isSelected)
        },
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        modifier = Modifier
          .padding(16.dp)
          .weight(1f),
        text = name,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.subtitle2,
      )

      RadioButton(
        modifier = Modifier
          .size(56.dp)
          .padding(16.dp),
        selected = isSelected,
        colors = RadioButtonDefaults.colors(
          unselectedColor = MaterialTheme.colors.onPrimary,
          selectedColor = MaterialTheme.colors.secondary
        ),
        onClick = {
          onClick(if (!isSelected) true else isSelected)
        },
      )
    }
  }
}

@Composable
fun ProgressDialog(
  onDismissRequest: () -> Unit = {},
  text: String = stringResource(id = R.string.all_loading),
  dismissOnClickOutside: Boolean = false,
) {
  Dialog(
    onDismissRequest = onDismissRequest,
    DialogProperties(
      dismissOnBackPress = dismissOnClickOutside,
      dismissOnClickOutside = dismissOnClickOutside
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .shadow(6.dp)
        .background(
          color = MaterialTheme.colors.surface,
          shape = RoundedCornerShape(8.dp)
        )
        .padding(24.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      CircularProgressIndicator(
        modifier = Modifier.size(44.dp),
        color = MaterialTheme.colors.secondary,
      )

      if (text.isNotEmpty()) {
        Spacer(modifier = Modifier.width(24.dp))
        Text(
          text = text,
          style = MaterialTheme.typography.subtitle2,
          color = MaterialTheme.colors.onSurface,
          maxLines = 1,
          textAlign = TextAlign.End
        )
      }
    }
  }
}

@Composable
fun AlertDialog(
  onDismissRequest: () -> Unit = {},
  title: String = "",
  subtitle: String = "",
  dismissOnClickOutside: Boolean = true,

  showCloseButton: Boolean = false,
  onCloseButtonClick: () -> Unit = {},

  leftButtonTitle: String = "",
  onLeftButtonClick: () -> Unit = {},

  rightButtonTitle: String = "",
  onRightButtonClick: () -> Unit = {},
) {
  Dialog(
    onDismissRequest = onDismissRequest,
    DialogProperties(
      dismissOnBackPress = dismissOnClickOutside,
      dismissOnClickOutside = dismissOnClickOutside
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 6.dp, shape = MaterialTheme.shapes.medium)
        .background(
          color = MaterialTheme.colors.surface,
          shape = MaterialTheme.shapes.medium
        )
        .padding(bottom = 32.dp),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        if (title.isNotEmpty()) {
          Text(
            modifier = Modifier
              .padding(start = 32.dp, top = 32.dp, end = 32.dp)
              .weight(1f),
            text = title,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSurface,
          )
        }

        if (showCloseButton) {
          IconButton(
            modifier = Modifier.padding(top = 16.dp, end = 16.dp),
            onClick = onCloseButtonClick,
            enabled = true,
          ) {
            Image(
              painter = painterResource(id = R.drawable.ic_button_close_grey),
              contentDescription = "Close",
            )
          }
        }
      }

      if (subtitle.isNotEmpty()) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(
          modifier = Modifier.padding(start = 32.dp, end = 32.dp),
          text = subtitle,
          style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
          color = MaterialTheme.colors.onSurface,
        )
      }

      if (leftButtonTitle.isNotEmpty() || rightButtonTitle.isNotEmpty()) {
        Spacer(modifier = Modifier.size(24.dp))
        Row(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
          if (leftButtonTitle.isNotEmpty()) {
            GreyButton(
              modifier = Modifier
                .height(64.dp)
                .defaultMinSize(minWidth = 186.dp)
                .weight(0.5f),
              onClick = onLeftButtonClick,
              text = leftButtonTitle,
            )
          }

          if (leftButtonTitle.isNotEmpty() && rightButtonTitle.isNotEmpty()) {
            Spacer(modifier = Modifier.size(8.dp))
          }

          if (rightButtonTitle.isNotEmpty()) {
            PrimaryButton(
              modifier = Modifier
                .height(64.dp)
                .defaultMinSize(minWidth = 186.dp)
                .weight(0.5f),
              onClick = onRightButtonClick,
              text = rightButtonTitle,
            )
          }
        }
      }
    }
  }
}

@Composable
fun NoInternetConnectionAlertDialog(
  title: String = stringResource(id = R.string.all_no_internet_connection),
  subtitle: String = stringResource(id = R.string.all_connect_to_wi_fi_network_or_enable_mobile_data_and_try_again),
  showCloseButton: Boolean = false,
  onCloseButtonClick: () -> Unit = {},

  showLeftButton: Boolean,
  leftButtonTitle: String = stringResource(id = R.string.all_cancel),
  onLeftButtonClick: () -> Unit = {},

  showRightButton: Boolean,
  rightButtonTitle: String = stringResource(id = R.string.all_try_again),
  onRightButtonClick: () -> Unit = {},
) {
  AlertDialog(
    title = title,
    subtitle = subtitle,
    showCloseButton = showCloseButton,
    onCloseButtonClick = onCloseButtonClick,

    leftButtonTitle = if (showLeftButton) leftButtonTitle else "",
    onLeftButtonClick = onLeftButtonClick,

    rightButtonTitle = if (showRightButton) rightButtonTitle else "",
    onRightButtonClick = onRightButtonClick,
  )
}

@Composable
fun ServerErrorAlertDialog(
  title: String = stringResource(id = R.string.all_whoops),
  subtitle: String = stringResource(id = R.string.all_sorry_something_went_wrong_give_it_another_try),
  showCloseButton: Boolean = false,
  onCloseButtonClick: () -> Unit = {},

  showLeftButton: Boolean,
  leftButtonTitle: String = stringResource(id = R.string.all_cancel),
  onLeftButtonClick: () -> Unit = {},

  showRightButton: Boolean,
  rightButtonTitle: String = stringResource(id = R.string.all_try_again),
  onRightButtonClick: () -> Unit = {},
) {
  AlertDialog(
    title = title,
    subtitle = subtitle,
    showCloseButton = showCloseButton,
    onCloseButtonClick = onCloseButtonClick,

    leftButtonTitle = if (showLeftButton) leftButtonTitle else "",
    onLeftButtonClick = onLeftButtonClick,

    rightButtonTitle = if (showRightButton) rightButtonTitle else "",
    onRightButtonClick = onRightButtonClick,
  )
}

@Composable
fun HandleDefaultErrors(state: ErrorState, callback: ErrorCallback) {
  if (state.showNoInternetConnectionDialog) {
    NoInternetConnectionAlertDialog(
      showLeftButton = state.showAlertDialogLeftButton,
      onLeftButtonClick = { callback.onNoInternetConnectionLeftButtonClick(state.actionId) },
      showRightButton = state.showAlertDialogRightButton,
      onRightButtonClick = { callback.onNoInternetConnectionRightButtonClick(state.actionId) },
    )
  }

  if (state.showServerErrorDialog) {
    ServerErrorAlertDialog(
      showLeftButton = state.showAlertDialogLeftButton,
      onLeftButtonClick = { callback.onNoInternetConnectionLeftButtonClick(state.actionId) },
      showRightButton = state.showAlertDialogRightButton,
      onRightButtonClick = { callback.onNoInternetConnectionRightButtonClick(state.actionId) },
    )
  }
}

@Composable
fun CircularProgressIndicatorUI(visible: Boolean) {
  AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = visible) {
    Box(
      modifier = Modifier
        .fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        color = MaterialTheme.colors.secondary
      )
    }
  }
}