package com.test.project.shared.compose.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.project.R
import com.test.project.shared.compose.theme.BackgroundVariant
import com.test.project.shared.compose.theme.Secondary
import com.test.project.shared.compose.theme.SecondaryVariant
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val AppBarHorizontalPadding = 4.dp

private val TitleIconModifier = Modifier
  .fillMaxHeight()
  .width(72.dp - AppBarHorizontalPadding)

private val TitleNoIconModifier = Modifier
  .fillMaxHeight()
  .width(20.dp)

@Composable
fun AppBar(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = AppBarDefaults.ContentPadding,
  showNavigationBackButton: Boolean = true,
  navigationBackButtonIcon: Painter = painterResource(id = R.drawable.ic_back),
  navigationBackButtonIconColor: ColorFilter = ColorFilter.tint(colorResource(id = R.color.black)),
  onNavigationBackButtonClick: () -> Unit = {},
  title: String = "",
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  elevation: Dp = 0.dp,
  actions: @Composable RowScope.() -> Unit = {}
) {
  TopAppBar(
    backgroundColor = backgroundColor,
    elevation = elevation,
    modifier = modifier
      .heightIn(min = 64.dp)
      .fillMaxWidth(),
    contentPadding = contentPadding,
    contentColor = contentColor
  ) {

    //TopAppBar Content
    Row(
      modifier = Modifier.height(40.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      //Navigation Icon
      Row(
        modifier = if (showNavigationBackButton) TitleIconModifier else TitleNoIconModifier,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CompositionLocalProvider(
          LocalContentAlpha provides ContentAlpha.high,
        ) {
          if (showNavigationBackButton) {
            IconButton(
              onClick = onNavigationBackButtonClick,
              enabled = true,
            ) {
              Image(
                colorFilter = navigationBackButtonIconColor,
                painter = navigationBackButtonIcon,
                contentDescription = "Back",
              )
            }
          } else Spacer(modifier = Modifier.fillMaxSize())
        }
      }

      //Title
      Row(
        modifier = Modifier
          .fillMaxHeight()
          .weight(1f),
        verticalAlignment = Alignment.CenterVertically
      ) {
        ProvideTextStyle(value = MaterialTheme.typography.h6) {
          CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
          ) {
            Text(
              textAlign = TextAlign.Start,
              maxLines = 1,
              style = MaterialTheme.typography.h6,
              color = MaterialTheme.colors.onSurface,
              text = title
            )
          }
        }
      }

      //actions
      CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Row(
          modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight(),
          horizontalArrangement = Arrangement.End,
          verticalAlignment = Alignment.CenterVertically,
          content = actions
        )
      }
    }
  }
}

@Composable
fun ToolbarIconButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  painter: Painter,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  elevation: ButtonElevation? = null,
  shape: Shape = MaterialTheme.shapes.small,
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.textButtonColors(),
  contentPadding: PaddingValues = PaddingValues(
    start = 8.dp,
    top = 0.dp,
    end = 8.dp,
    bottom = 0.dp
  ),
) {
  CustomButton(
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    indication = null,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    onClick = onClick,
    content = {
      Image(
        painter = painter,
        contentDescription = null,
      )
    }
  )
}

@Composable
fun ToolbarTextButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  text: String,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  elevation: ButtonElevation? = null,
  shape: Shape = MaterialTheme.shapes.small,
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.textButtonColors(),
  contentPadding: PaddingValues = PaddingValues(
    start = 8.dp,
    top = 0.dp,
    end = 8.dp,
    bottom = 0.dp
  ),
) {
  val isPressed by interactionSource.collectIsPressedAsState()
  CustomButton(
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    indication = null,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    onClick = onClick,
    content = {
      Text(
        text = text,
        color = if (isPressed) SecondaryVariant else Secondary,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.41).sp,
        maxLines = 1,
      )
    }
  )
}

@Composable
fun SetSystemUiPrimarySurfaceColor() {
  val systemUiController = rememberSystemUiController()
  val color = MaterialTheme.colors.primarySurface
  SideEffect {
    systemUiController.setSystemBarsColor(
      color = color,
    )
  }
}

@Composable
fun SetSystemUiSecondaryColor() {
  val systemUiController = rememberSystemUiController()
  val color = MaterialTheme.colors.secondary
  SideEffect {
    systemUiController.setSystemBarsColor(
      color = color,
    )
  }
}

@Composable
fun SetDarkSystemUi() {
  val systemUiController = rememberSystemUiController()
  SideEffect {
    systemUiController.setSystemBarsColor(
      color = BackgroundVariant,
    )
  }
}