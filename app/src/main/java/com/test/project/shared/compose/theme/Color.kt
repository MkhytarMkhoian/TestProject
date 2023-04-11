package com.test.project.shared.compose.theme

import androidx.compose.ui.graphics.Color

val Primary = Color(0xffF2F2F7)
val PrimaryVariant = Color(0xff8F8F93)
val onPrimary = Color(0xff3B3B3D)
val SecondaryVariant = Color(0xffFF8F6C)
val Secondary = Color(0xffFF5C40)
val Error = Color(0xffFF1A1A)
val onError = Color.White
val onBackground = Color(0xff797980)
val BackgroundVariant = Color(0xff1C1C1E)
val WhiteAlpha70 = Color.White.copy(alpha = 0.7f)
val PrimaryGreen = Color(0xff00C853)
val Orange = Color(0xffFF9500)
val LightGrey = Color(0xffE5E5EA).copy(alpha = 0.5f)
val LabelDark = Color(0xffEBEBF5).copy(alpha = 0.3f)

/**
 * This is the minimum amount of calculated contrast for a color to be used on top of the
 * surface color. These values are defined within the WCAG AA guidelines, and we use a value of
 * 3:1 which is the minimum for user-interface components.
 */
const val MinContrastOfPrimaryVsSurface = 3f
