package com.test.project.shared.compose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.test.project.R
import com.test.project.shared.compose.theme.BackgroundVariant

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