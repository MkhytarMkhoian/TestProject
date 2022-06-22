package com.test.project.shared.api.network

import com.test.project.shared.models.ErrorMessage
import com.test.project.shared.models.Result
import com.squareup.moshi.Moshi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorParser @Inject constructor(
  private val moshi: Moshi
) {

  private fun getErrorMessage(rawBody: String): ErrorMessage {
    return moshi.adapter(ErrorMessage::class.java)
      .serializeNulls()
      .lenient()
      .nullSafe()
      .fromJson(rawBody)!!
  }

  private fun parseErrorResponse(rawResponse: String?): Result.Error {
    if (rawResponse.isNullOrEmpty()) {
      return Result.Error.GenericError(
        IllegalArgumentException("empty error body")
      )
    } else if (!isJSON(rawResponse)) {
      return Result.Error.RequestError(
        ErrorMessage(
          "404",
          "Not valid JSON, most likely it's XML"
        )
      )
    }

    val error = getErrorMessage(rawResponse)

    return Result.Error.RequestError(
      ErrorMessage(
        error.errorCode,
        error.errorDescription
      )
    )
  }

  fun <T> handleError(response: Response<T>): Result.Error {
    val code = response.code()

    return if (code in 500..599) {
      Result.Error.ServerError(code)
    } else {
      return parseErrorResponse(response.errorBody()?.string())
    }
  }

  private fun isJSON(rawString: String): Boolean {
    try {
      JSONObject(rawString)
    } catch (ex: JSONException) {
      try {
        JSONArray(rawString)
      } catch (ex1: JSONException) {
        return false
      }
    }
    return true
  }

}
