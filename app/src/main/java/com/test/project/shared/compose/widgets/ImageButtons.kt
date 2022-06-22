package com.test.project.shared.compose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun CircleImageButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  painter: Painter,
  backgroundColor: Color
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = remember { MutableInteractionSource() },
    elevation = null,
    shape = CircleShape,
    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
  ) {
    Image(
      painter = painter,
      contentDescription = null,
      modifier = Modifier.align(Alignment.CenterVertically)
    )
  }
}
