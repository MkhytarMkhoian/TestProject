package com.test.project.shared.extensions

import android.content.res.Resources
import android.os.Build
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

const val CURRENCY_SIGN = "$"
val PRICE_FORMAT = DecimalFormat("${CURRENCY_SIGN}0.00").applyDecimalSeparator()
val PRICE_FORMAT_NEGATIVE = DecimalFormat("-${CURRENCY_SIGN}0.00").applyDecimalSeparator()
val PRICE_FORMAT_ADDITIONAL = DecimalFormat("${CURRENCY_SIGN}0.00").applyDecimalSeparator()
val PRICE_FORMAT_SHORT = DecimalFormat("${CURRENCY_SIGN}0.##").applyDecimalSeparator()

fun DecimalFormat.applyDecimalSeparator(): DecimalFormat {
  val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Resources.getSystem().configuration.locales[0]
  } else {
    Resources.getSystem().configuration.locale
  }
  val format = DecimalFormatSymbols(locale)
  format.decimalSeparator = '.'
  decimalFormatSymbols = format
  return this
}

fun Int.toBoolean() = this == 1

fun Int.toPrice(): String = PRICE_FORMAT.format(this * 0.01)

fun Int.toPriceShort(): String = PRICE_FORMAT_SHORT.format(this * 0.01)

fun Int.toPriceNegative(): String = PRICE_FORMAT_NEGATIVE.format(this * 0.01)

fun Int.toPriceAdditional(): String = PRICE_FORMAT_ADDITIONAL.format(this * 0.01)

fun Int.toPercent(): String = "$this%"

fun Boolean.toInt() = if (this) 1 else 0
