package com.test.project.shared.extensions

import com.test.project.shared.models.Result

class DeveloperException(developerMessage: String, cause: Throwable? = null) :
  Exception("Developer run into mistake: $developerMessage", cause)

fun wtf(
  msg: String = "Something went wrong and this is pretty confusing for a developer.",
  cause: Throwable? = null
): Nothing = throw DeveloperException(msg, cause)

fun handleDefaultErrors(
  error: Result.Error,
  onNoInternetConnection: (Result.Error.NoInternetConnection) -> Unit = {},
  onServerError: (Result.Error.ServerError) -> Unit = {},
  onRequestError: (Result.Error.RequestError) -> Unit = {},
  onGenericError: (Result.Error.GenericError) -> Unit = {},
) {
  when (error) {
    is Result.Error.RequestError -> onRequestError(error)
    is Result.Error.GenericError -> onGenericError(error)
    is Result.Error.NoInternetConnection -> onNoInternetConnection(error)
    is Result.Error.ServerError -> onServerError(error)
  }
}