package com.test.project.shared.validators

import com.test.project.R
import com.test.project.shared.resources.ResStorage
import javax.inject.Inject

class PasswordValidator @Inject constructor(
  private val resStorage: ResStorage
) : Validator<String> {
  override fun validate(value: String): ValidationResult {
    return when {
      value.isEmpty() -> return ValidationResult.Invalid(resStorage.getString(R.string.login__please_enter_password))
      else -> ValidationResult.Valid()
    }
  }
}