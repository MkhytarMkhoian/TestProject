package com.test.project.shared.compose.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.project.R

@Composable
fun ThreeBubbleLoader(modifier: Modifier = Modifier) {
  Box(
    modifier.width(56.dp)
  ) {
    val duration = 600
    val delay = duration / 3

    LoaderBubble(
      duration = duration,
      delay = delay,
      color = colorResource(R.color.grey_b),
      modifier = Modifier
        .align(Alignment.CenterStart)
        .padding(start = 9.dp)
    )

    LoaderBubble(
      duration = duration,
      delay = delay * 2,
      color = colorResource(R.color.grey_b),
      modifier = Modifier.align(Alignment.Center)
    )

    LoaderBubble(
      duration = duration,
      delay = delay * 3,
      color = colorResource(R.color.grey_b),
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 9.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun ThreeBubbleLoaderPreview() {
  ThreeBubbleLoader()
}
