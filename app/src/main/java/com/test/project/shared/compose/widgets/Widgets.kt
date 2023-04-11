package com.test.project.shared.compose.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.project.R
import com.test.project.shared.compose.theme.PrimaryGreen

@Composable
fun LoadingUI(visible: Boolean) {
  AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = visible) {
    Box(
      modifier = Modifier
        .fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(color = PrimaryGreen)
    }
  }
}

@Composable
fun EmptyViewUI(
  modifier: Modifier = Modifier,
  icon: Painter? = null,
  title: String = "",
  message: String = "",
  showActionButton: Boolean = false,
  onActionClick: () -> Unit = {},
  actionButtonText: String = "",
  showNetworkError: Boolean = false,
  showUnexpectedError: Boolean = false,
) {
  var internalIcon = icon
  var internalTitle = title
  var internalMessage = message
  var internalActionButtonText = actionButtonText
  var internalShowActionButton = showActionButton

  if (showNetworkError) {
    internalTitle = stringResource(id = R.string.all_no_internet_connection)
    internalMessage = stringResource(id = R.string.error_internet_connection)
    internalIcon = painterResource(R.drawable.ic_warning)
    internalActionButtonText = stringResource(id = R.string.all_retry)
    internalShowActionButton = true
  }

  if (showUnexpectedError) {
    internalTitle = stringResource(id = R.string.all_whoops)
    internalMessage = stringResource(id = R.string.all_error_something_happened)
    internalIcon = painterResource(R.drawable.ic_warning)
    internalActionButtonText = stringResource(id = R.string.all_retry)
    internalShowActionButton = true
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 32.dp, end = 32.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = modifier,
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      internalIcon?.let {
        Image(
          modifier = Modifier.size(48.dp),
          painter = internalIcon,
          contentDescription = ""
        )
        Spacer(modifier = Modifier.size(24.dp))
      }

      if (internalTitle.isNotEmpty()) {
        Text(
          modifier = Modifier
            .padding(bottom = 12.dp),
          text = internalTitle,
          color = colorResource(id = R.color.primary_text),
          style = MaterialTheme.typography.h5,
          textAlign = TextAlign.Center
        )
      }

      if (internalMessage.isNotEmpty()) {
        Text(
          modifier = Modifier
            .padding(bottom = 32.dp),
          text = internalMessage,
          color = colorResource(id = R.color.secondary_text),
          style = MaterialTheme.typography.subtitle1,
          textAlign = TextAlign.Center
        )
      }

      if (internalShowActionButton) {
        SubmitButton(
          modifier = Modifier
            .height(48.dp)
            .wrapContentWidth()
            .padding(start = 16.dp, end = 16.dp)
            .widthIn(min = 140.dp, max = 300.dp),
          text = internalActionButtonText,
          onClick = onActionClick,
        )
        Spacer(modifier = Modifier.size(16.dp))
      }
    }
  }
}

@Composable
fun SubmitButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  text: String,
  enabled: Boolean = true,
  isLoading: Boolean = false,
  elevation: ButtonElevation? = null,
  progressIndicatorModifier: Modifier = Modifier.size(24.dp),
  buttonContentModifier: Modifier = Modifier.fillMaxWidth(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  shape: Shape = MaterialTheme.shapes.large,
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = PrimaryGreen,
    contentColor = colorResource(id = R.color.themed_submit_button_text_color),
    disabledBackgroundColor = colorResource(id = R.color.themed_button_disabled_background),
    disabledContentColor = colorResource(id = R.color.themed_submit_button_disabled_text_color),
  ),
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  progressColor: Color = colorResource(id = R.color.white),
  icon: Painter? = null,
  isSmall: Boolean = false,
) {
  Button(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    shape = shape,
    elevation = elevation,
    border = border,
    colors = colors,
    interactionSource = interactionSource,
    contentPadding = contentPadding,
  ) {
    ButtonContent(
      buttonContentModifier = buttonContentModifier,
      progressIndicatorModifier = progressIndicatorModifier,
      progressColor = progressColor,
      isLoading = isLoading,
      text = text,
      icon = icon,
      isSmall = isSmall
    )
  }
}

@Composable
private fun RowScope.ButtonContent(
  buttonContentModifier: Modifier = Modifier,
  progressIndicatorModifier: Modifier = Modifier,
  isLoading: Boolean,
  text: String,
  progressColor: Color = MaterialTheme.colors.primary,
  strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
  icon: Painter? = null,
  isSmall: Boolean = false
) {
  AnimatedVisibility(
    enter = fadeIn(),
    exit = fadeOut(),
    visible = isLoading
  ) {
    Box(
      modifier = buttonContentModifier,
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        modifier = progressIndicatorModifier,
        color = progressColor,
        strokeWidth = strokeWidth,
      )
    }
  }

  AnimatedVisibility(
    enter = fadeIn(),
    exit = fadeOut(),
    visible = !isLoading
  ) {
    Row(
      modifier = buttonContentModifier,
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      icon?.let {
        Image(
          modifier = Modifier
            .size(16.dp),
          painter = icon,
          colorFilter = ColorFilter.tint(progressColor),
          contentDescription = "icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        style = if (isSmall)
          MaterialTheme.typography.body2 else
          MaterialTheme.typography.button,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}