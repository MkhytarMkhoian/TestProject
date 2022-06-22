package com.test.project.shared.compose.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.project.R

@Composable
fun GreyButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  text: String,
  enabled: Boolean = true,
  isLoading: Boolean = false,
  elevation: ButtonElevation? = null,
  buttonContentModifier: Modifier = Modifier.fillMaxWidth(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  indication: Indication? = rememberRipple(color = colorResource(id = R.color.paco)),
  shape: Shape = RoundedCornerShape(32.dp),
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = MaterialTheme.colors.primaryVariant,
    disabledBackgroundColor = colorResource(id = R.color.mid_grey),
    disabledContentColor = MaterialTheme.colors.onSecondary,
  ),
  contentPadding: PaddingValues = PaddingValues(
    start = 0.dp,
    top = 0.dp,
    end = 0.dp,
    bottom = 0.dp
  ),
  icon: Painter? = null,
  textStyle: TextStyle = MaterialTheme.typography.button,
) {
  CustomButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    shape = shape,
    elevation = elevation,
    border = border,
    colors = colors,
    indication = indication,
    interactionSource = interactionSource,
    contentPadding = contentPadding,
  ) {
    ButtonContent(
      buttonContentModifier = buttonContentModifier,
      isLoading = isLoading,
      loaderBackgroundColor = colors.backgroundColor(enabled = true).value,
      text = text,
      icon = icon,
      textStyle = textStyle,
    )
  }
}

@Composable
fun PrimaryButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  text: String,
  enabled: Boolean = true,
  isLoading: Boolean = false,
  elevation: ButtonElevation? = null,
  buttonContentModifier: Modifier = Modifier.fillMaxWidth(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  indication: Indication? = rememberRipple(color = colorResource(id = R.color.paco)),
  shape: Shape = RoundedCornerShape(32.dp),
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = MaterialTheme.colors.secondary,
    contentColor = MaterialTheme.colors.onSecondary,
    disabledBackgroundColor = colorResource(id = R.color.mid_grey),
    disabledContentColor = MaterialTheme.colors.onSecondary,
  ),
  contentPadding: PaddingValues = PaddingValues(
    start = 0.dp,
    top = 0.dp,
    end = 0.dp,
    bottom = 0.dp
  ),
  icon: Painter? = null,
  textStyle: TextStyle = MaterialTheme.typography.button,
) {
  CustomButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    shape = shape,
    elevation = elevation,
    border = border,
    colors = colors,
    indication = indication,
    interactionSource = interactionSource,
    contentPadding = contentPadding,
  ) {
    ButtonContent(
      buttonContentModifier = buttonContentModifier,
      isLoading = isLoading,
      loaderBackgroundColor = colors.backgroundColor(enabled = true).value,
      text = text,
      icon = icon,
      textStyle = textStyle,
    )
  }
}

@Composable
private fun RowScope.ButtonContent(
  buttonContentModifier: Modifier = Modifier,
  loaderBackgroundColor: Color = Color.Transparent,
  isLoading: Boolean,
  text: String,
  icon: Painter? = null,
  textStyle: TextStyle = MaterialTheme.typography.button,
) {
  if (isLoading) {
    Box(
      modifier = buttonContentModifier
        .background(loaderBackgroundColor),
      contentAlignment = Alignment.Center
    ) {
      BubbleLoader(
        modifier = Modifier
          .width(56.dp)
          .fillMaxHeight()
          .background(
            color = loaderBackgroundColor,
            shape = CircleShape
          )
      )
    }
  } else {
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
          contentDescription = "icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        style = textStyle,
        text = text,
      )
    }
  }
}

@Composable
fun CustomButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  indication: Indication? = rememberRipple(),
  elevation: ButtonElevation? = ButtonDefaults.elevation(),
  shape: Shape = MaterialTheme.shapes.small,
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  content: @Composable RowScope.() -> Unit
) {
  val contentColor by colors.contentColor(enabled)
  CustomSurface(
    modifier = modifier,
    shape = shape,
    color = colors.backgroundColor(enabled).value,
    contentColor = contentColor.copy(alpha = 1f),
    border = border,
    elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
    onClick = onClick,
    enabled = enabled,
    role = Role.Button,
    interactionSource = interactionSource,
    indication = indication
  ) {
    CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
      ProvideTextStyle(
        value = MaterialTheme.typography.button
      ) {
        Row(
          Modifier
            .defaultMinSize(
              minWidth = ButtonDefaults.MinWidth,
              minHeight = ButtonDefaults.MinHeight
            )
            .padding(contentPadding),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically,
          content = content
        )
      }
    }
  }
}

@Composable
fun CustomSurface(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  color: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(color),
  border: BorderStroke? = null,
  elevation: Dp = 0.dp,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  indication: Indication? = LocalIndication.current,
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  content: @Composable () -> Unit
) {
  val absoluteElevation = LocalAbsoluteElevation.current + elevation
  CompositionLocalProvider(
    LocalContentColor provides contentColor,
    LocalAbsoluteElevation provides absoluteElevation
  ) {
    Box(
      modifier
        .surface(
          shape = shape,
          backgroundColor = surfaceColorAtElevation(
            color = color,
            elevationOverlay = LocalElevationOverlay.current,
            absoluteElevation = absoluteElevation
          ),
          border = border,
          elevation = elevation
        )
        .then(
          Modifier.clickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = onClick
          )
        ),
      propagateMinConstraints = true
    ) {
      content()
    }
  }
}

private fun Modifier.surface(
  shape: Shape,
  backgroundColor: Color,
  border: BorderStroke?,
  elevation: Dp
) = this
  .shadow(elevation, shape, clip = false)
  .then(if (border != null) Modifier.border(border, shape) else Modifier)
  .background(color = backgroundColor, shape = shape)
  .clip(shape)

@Composable
private fun surfaceColorAtElevation(
  color: Color,
  elevationOverlay: ElevationOverlay?,
  absoluteElevation: Dp
): Color {
  return if (color == MaterialTheme.colors.surface && elevationOverlay != null) {
    elevationOverlay.apply(color, absoluteElevation)
  } else {
    color
  }
}

@Composable
fun PrimaryOutlinedButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  text: String,
  enabled: Boolean = true,
  isLoading: Boolean = false,
  elevation: ButtonElevation? = null,
  buttonContentModifier: Modifier = Modifier.fillMaxWidth(),
  contentColor: Color = MaterialTheme.colors.secondary,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  shape: Shape = MaterialTheme.shapes.large,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  borderColor: Color = Color.Transparent,
  border: BorderStroke? = BorderStroke(
    ButtonDefaults.OutlinedBorderSize,
    borderColor
  ),
  icon: Painter? = null,
  textStyle: TextStyle = MaterialTheme.typography.subtitle2,
) {

  OutlinedButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    shape = shape,
    elevation = elevation,
    border = border,
    colors = ButtonDefaults.outlinedButtonColors(
      contentColor = contentColor,
      backgroundColor = Color.Transparent,
    ),
    interactionSource = interactionSource,
    contentPadding = contentPadding,
  ) {
    ButtonContent(
      buttonContentModifier = buttonContentModifier,
      isLoading = isLoading,
      text = text,
      icon = icon,
      textStyle = textStyle,
    )
  }
}

@Composable
fun NavigationMenuButtonUI(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  indication: Indication? = rememberRipple(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  elevation: ButtonElevation? = null,
  shape: Shape = MaterialTheme.shapes.small,
  border: BorderStroke? = null,
  colors: ButtonColors = ButtonDefaults.buttonColors(
    contentColor = Color.White,
    backgroundColor = Color.Transparent,
    disabledContentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
    disabledBackgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.18f),
  ),
  contentPadding: PaddingValues = PaddingValues(
    start = 8.dp,
    top = 0.dp,
    end = 8.dp,
    bottom = 0.dp
  )
) {
  CustomButton(
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    indication = indication,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    onClick = onClick,
    content = {
      Image(
        painter = painterResource(id = R.drawable.ic_menu),
        alpha = if (!enabled) 0.5f else 1f,
        contentDescription = null
      )
    }
  )
}
