package com.test.project.shared.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.test.project.R

@Composable
fun FullScreenLoader(modifier: Modifier = Modifier) {
  Box(
    modifier
      .fillMaxSize()
      .background(color = colorResource(R.color.white_transparent_70))
  ) {
    ThreeBubbleLoader(
      Modifier.align(Alignment.Center)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun FullScreenLoaderPreview() {
  FullScreenLoader()
}
