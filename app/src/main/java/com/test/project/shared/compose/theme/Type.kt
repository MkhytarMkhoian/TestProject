package com.test.project.shared.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.project.R

val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_bold, FontWeight.W700)
)

val AppTypography = Typography(
    h4 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.36.sp
    ),
    h5 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.36.sp
    ),
    h6 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.38.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.41).sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.24).sp
    ),
    body1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = (-0.41).sp
    ),
    button = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    caption = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.08).sp
    ),
    overline = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W700,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.01).sp
    )
)
