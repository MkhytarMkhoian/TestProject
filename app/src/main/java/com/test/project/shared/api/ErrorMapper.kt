package com.test.project.shared.api

import com.squareup.moshi.JsonDataException
import com.test.project.shared.api.ex.NetworkException
import com.test.project.shared.api.ex.UnauthorisedException
import com.test.project.shared.api.ex.UnexpectedException
import com.test.project.shared.interactors.ErrorMessageInteractor
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorMapper @Inject constructor(
  private val errorMessageInteractor: ErrorMessageInteractor,
) {

  companion object {
    const val UNAUTHORISED_HTTP_CODE = 401
  }

  suspend fun mapError(
    e: Throwable,
    method: String,  // TODO report to crashlytics
    handleUnauthorisedApiException: (suspend (Throwable) -> Throwable)? = null
  ): Throwable {
    var convertedException: Throwable? = null
    when (e) {
      is IOException -> convertedException =
        NetworkException(errorMessageInteractor.getNetworkProblemError(), e)
      is HttpException -> convertedException = createApiException(e)
      is JsonDataException -> convertedException = createUnexpectedException(e, -1)
    }

    if (convertedException != null) {
      // TODO report to crashlytics
      convertedException.printStackTrace()
      return handleUnauthorisedApiException?.invoke(convertedException) ?: convertedException
    }

    return e
  }

  private fun createApiException(e: Throwable): Throwable {
    val convertedException: Throwable
    val httpEx = e as HttpException
    val httpCode = httpEx.code()

    // TODO report to crashlytics

    convertedException = try {
      if (httpCode == UNAUTHORISED_HTTP_CODE) {
        UnauthorisedException(e)

      } else {
        createUnexpectedException(e, httpCode)
      }
    } catch (ex: IOException) {
      // TODO report to crashlytics
      createUnexpectedException(e, httpCode)
    } catch (ex: NullPointerException) {
      // TODO report to crashlytics
      createUnexpectedException(e, httpCode)
    }

    return convertedException
  }

  private fun createUnexpectedException(e: Throwable, httpCode: Int): Throwable {
    e.printStackTrace()
    return UnexpectedException(errorMessageInteractor.getUnknownProblemError(), e, httpCode)
  }
}
