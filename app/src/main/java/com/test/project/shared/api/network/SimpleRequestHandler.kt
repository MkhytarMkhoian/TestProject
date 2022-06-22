package com.test.project.shared.api.network

import com.test.project.shared.crashlytics.CrashReporting
import com.test.project.shared.models.Result
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SimpleRequestHandler<ResponseType, ResultType : Any>(
  private val crashReporting: CrashReporting,
  private val networkManager: NetworkManager,
  private val errorParser: ErrorParser,
  private val onRequest: suspend () -> Response<ResponseType>,
  private val onResult: suspend (responseBody: ResponseType) -> Result<ResultType>,
  private val withoutBody: Boolean = false
) {

  suspend fun request(): Result<ResultType> {
    if (!networkManager.isOnline()) return Result.Error.NoInternetConnection

    val response: Response<ResponseType>

    try {
      response = onRequest.invoke()
    } catch (e: SocketTimeoutException) {
      Timber.w(e)
      crashReporting.log(e, CrashReporting.TAG_NETWORK, message = "SocketTimeoutException")
      return Result.Error.NoInternetConnection
    } catch (e: UnknownHostException) {
      Timber.w(e)
      crashReporting.log(e, CrashReporting.TAG_NETWORK, message = "UnknownHostException")
      return Result.Error.ServerError(599)
    } catch (e: Exception) {
      Timber.w(e)
      crashReporting.log(e, CrashReporting.TAG_NETWORK)
      return Result.Error.GenericError(e)
    }

    if (response.isSuccessful) {
      if (withoutBody) {
        return onResult.invoke(response.body()!!)
      }

      val responseBody = response.body() ?: return Result.Error.GenericError(
        IllegalArgumentException("empty body")
      )

      return onResult.invoke(responseBody)
    } else {
      crashReporting.log(Error(response.toString()), CrashReporting.TAG_NETWORK)
      return errorParser.handleError(response)
    }
  }

}
