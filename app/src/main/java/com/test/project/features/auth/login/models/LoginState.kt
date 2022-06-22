package com.test.project.features.auth.login.models

import com.test.project.shared.models.InputItem
import com.test.project.shared.state.ErrorState

data class LoginState(
  override val showNoInternetConnectionDialog: Boolean = false,
  override val showServerErrorDialog: Boolean = false,
  override val showAlertDialogLeftButton: Boolean = false,
  override val showAlertDialogRightButton: Boolean = true,
  override val actionId: String = "",
  val isLoading: Boolean = false,
  val wasErrorFromBackend: Boolean = false,
  val email: InputItem,
  val password: InputItem,
): ErrorState
