package com.test.project.shared.validators

interface Validator<T> {
  fun validate(value: T): ValidationResult
}

sealed class ValidationResult(open val message: String) {
  data class Valid(override val message: String = "") : ValidationResult(message)
  data class Invalid(override val message: String) : ValidationResult(message = message)

  fun isValid(): Boolean {
    return this is Valid
  }

  fun isInvalid(): Boolean = !isValid()
}