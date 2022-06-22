package com.test.project.features.auth.login.models

data class LoginValidationResult(
  val state: LoginState,
  val isInvalid: Boolean,
)
