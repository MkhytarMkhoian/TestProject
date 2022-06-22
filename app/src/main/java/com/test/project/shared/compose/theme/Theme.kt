package com.test.project.shared.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
  primary = Primary,
  primaryVariant = PrimaryVariant,
  onPrimary = onPrimary,

  surface = Color.White,
  onSurface = Color.Black,

  secondary = Secondary,
  secondaryVariant = SecondaryVariant,
  onSecondary = Color.White,

  error = Error,
  onError = onError,

  background = Primary,
  onBackground = onBackground
)

@Composable
fun AppTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = LightColors,
    typography = AppTypography,
    shapes = AppShapes,
    content = content
  )
}
