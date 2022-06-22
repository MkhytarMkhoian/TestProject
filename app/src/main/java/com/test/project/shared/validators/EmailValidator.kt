package com.test.project.shared.validators

import com.test.project.R
import com.test.project.shared.resources.ResStorage
import java.util.regex.Pattern
import javax.inject.Inject

class EmailValidator @Inject constructor(
  private val resStorage: ResStorage
) : Validator<String> {

  override fun validate(value: String): ValidationResult {
    if (value.isEmpty()) {
      return ValidationResult.Invalid(resStorage.getString(R.string.login__please_enter_email))

    } else if (!EMAIL_PATTERN.matcher(value).matches()) {
      return ValidationResult.Invalid(resStorage.getString(R.string.login__the_email_your_entered_is_incorrect_please_try_again))
    }
    return ValidationResult.Valid()
  }

  private companion object {
    private val EMAIL_PATTERN: Pattern = Pattern.compile(
      "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
          "\\@" +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
          "(" +
          "\\." +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
          ")+"
    )
  }
}