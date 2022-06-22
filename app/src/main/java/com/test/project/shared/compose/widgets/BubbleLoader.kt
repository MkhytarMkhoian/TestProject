package com.test.project.shared.compose.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.project.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DefaultBubbleLoader() {
  BubbleLoader(
    modifier = Modifier
      .width(56.dp)
      .height(56.dp)
      .background(
        color = colorResource(R.color.light_grey),
        shape = CircleShape
      )
  )
}

@Composable
fun BubbleLoader(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
  ) {
    val duration = 600
    val delay = duration / 3

    LoaderBubble(
      duration = duration,
      delay = delay,
      modifier = Modifier
        .align(Alignment.CenterStart)
        .padding(start = 9.dp)
    )

    LoaderBubble(
      duration = duration,
      delay = delay * 2,
      modifier = Modifier.align(Alignment.Center)
    )

    LoaderBubble(
      duration = duration,
      delay = delay * 3,
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 9.dp)
    )
  }
}

@Composable
fun LoaderBubble(
  duration: Int,
  delay: Int,
  modifier: Modifier = Modifier,
  color: Color = colorResource(R.color.white)
) {
  val initial = 10.dp.value
  val target = 6.dp.value

  val animation = remember { Animatable(initial) }

  LaunchedEffect(animation) {
    delay(delay.toLong())

    launch {
      animation.animateTo(
        targetValue = target,
        animationSpec = infiniteRepeatable(
          animation = tween(duration),
          repeatMode = RepeatMode.Reverse
        )
      )
    }
  }

  Box(
    modifier.size(10.dp)
  ) {
    Box(
      Modifier
        .size(animation.value.dp)
        .background(
          color = color,
          shape = CircleShape
        )
        .align(Alignment.Center)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun BubbleLoaderPreview() {
  BubbleLoader()
}
