package com.test.project.shared.state

interface ErrorState {
  val showNoInternetConnectionDialog: Boolean
  val showServerErrorDialog: Boolean
  val showAlertDialogLeftButton: Boolean
  val showAlertDialogRightButton: Boolean
  val actionId: String
}