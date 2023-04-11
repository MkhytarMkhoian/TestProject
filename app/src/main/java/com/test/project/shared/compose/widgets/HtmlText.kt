package com.test.project.shared.compose.widgets

import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(text: String, @StyleRes style: Int, modifier: Modifier = Modifier) {
  val textWithNewLinesFormatted = text.replace("\r\n", "<br/>")

  AndroidView(
    modifier = modifier,
    factory = { context -> TextView(context, null, 0, style) },
    update = {
      it.text = HtmlCompat.fromHtml(textWithNewLinesFormatted, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    }
  )
}
