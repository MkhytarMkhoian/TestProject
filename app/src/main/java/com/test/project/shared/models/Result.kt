package com.test.project.shared.models

sealed class Result<out T : Any> {

  /**
   * Base wrapper class for successful data request
   * @return requested data, if data processing was correct
   */
  data class Success<out T : Any>(val data: T?) : Result<T>()

  /**
   * Base class for unsuccessful data request
   * Don't use base class, always use child error class for describing error type
   */
  sealed class Error : Result<Nothing>() {

    /**
     * Wrapper class for handling exceptions, such as IO, NPE or custom,
     * except SocketTimeoutException (@see [Result.Error.NoInternetConnection])
     */
    data class GenericError(val exception: Throwable) : Error()

    /**
     * Wrapper class for 4XX HTTP errors without any additional data (only inner code and message)
     */
    data class RequestError(val errorMessage: ErrorMessage) : Error()

    /**
     * Wrapper class for case when internet connection is missing or bad
     * and request raises [java.net.SocketTimeoutException]
     */
    object NoInternetConnection : Error()

    /**
     * Wrapper class for 5XX HTTP errors.
     */
    data class ServerError(val code: Int) : Error()
  }

}

class RequestException(message: String) : Exception(message)

fun <T : Any> Result<T>.successDataOrNull(): T? {
  return when (this) {
    is Result.Success -> this.data
    else -> null
  }
}
