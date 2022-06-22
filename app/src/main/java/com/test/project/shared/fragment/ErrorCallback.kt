package com.test.project.shared.fragment

interface ErrorCallback {
  val onNoInternetConnectionLeftButtonClick: (String) -> Unit
  val onNoInternetConnectionRightButtonClick: (String) -> Unit
  val onServerErrorLeftButtonClick: (String) -> Unit
  val onServerErrorRightButtonClick: (String) -> Unit
}