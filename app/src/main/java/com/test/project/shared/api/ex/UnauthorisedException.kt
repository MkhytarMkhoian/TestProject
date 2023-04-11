package com.test.project.shared.api.ex

class UnauthorisedException(
  cause: Throwable?
) : BaseApiException("", cause) {

  override fun getFirstErrorMsg(): String = ""

  override fun httpStatusCode(): Int {
    return 401
  }
}