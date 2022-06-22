package  com.test.project.shared.api.interceptors

import com.test.project.features.auth.interactors.RefreshTokenInteractor
import com.test.project.shared.models.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException
import javax.inject.Inject

class UserTokenAuthenticator @Inject constructor(
  private val interactor: RefreshTokenInteractor,
) : Authenticator {

  companion object {
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"
  }

  @Throws(IOException::class)
  override fun authenticate(route: Route?, response: Response): Request? {
    if (responseCount(response) >= 2) {
      return null // If we've failed 2 times, give up. - in real life, never give up!!
    }

    return runBlocking {
      val result = interactor.refreshToken()
      if (result is Result.Success) {
        val token = result.data!!

        return@runBlocking response.request.newBuilder()
          .header(AUTHORIZATION_HEADER_KEY, "Bearer $token")
          .build()
      } else null
    }
  }

  private fun responseCount(r: Response): Int {
    var response = r
    var result = 1
    while (response.priorResponse?.also { response = it } != null) {
      result++
    }
    return result
  }
}