package  com.test.project.shared.api.interceptors

import android.content.Context
import com.test.project.BuildConfig
import com.test.project.shared.storage.TokenDataStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
  private val context: Context,
  private val dataStorage: TokenDataStorage,
) : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(addHeaders(chain))
  }

  private fun addHeaders(chain: Interceptor.Chain): Request {
    val request = chain.request()
    val builder: Request.Builder = request.newBuilder()
    if (chain.request().headers.values(NO_HEADERS_MARKER).isEmpty()) {
      builder.addHeader(APP_VERSION_HEADER_KEY, BuildConfig.VERSION_CODE.toString())
      builder.addHeader(USER_AGENT_HEADER_KEY, getUserAgent(context))
      builder.addHeader(DEVICE_HEADER_KEY, DEVICE)
      builder.addHeader(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE)
      builder.addHeader(ACCEPT_HEADER_KEY, JSON_ACCEPT_TYPE)

      runBlocking {
        dataStorage.getAccessToken()?.let { token ->
          builder.addHeader(AUTHORIZATION_HEADER_KEY, "Bearer $token")
        }
      }

    } else {
      builder.removeHeader(NO_HEADERS_MARKER)
    }
    return builder.build()
  }

  private fun getUserAgent(context: Context): String {
    val systemUserAgent = System.getProperty("http.agent")
    val customUserAgent: String
    try {
      customUserAgent = "$systemUserAgent; Allset Merchant Android ${context.packageManager
        .getPackageInfo(context.packageName, 0).versionName}"
    } catch (e: Exception) {
      return systemUserAgent!!
    }
    return customUserAgent
  }

  companion object {
    const val DEVICE = "android"
    private const val JSON_CONTENT_TYPE = "application/json"
    private const val JSON_ACCEPT_TYPE = "application/json"

    private const val USER_AGENT_HEADER_KEY = "User-Agent"
    private const val APP_VERSION_HEADER_KEY = "App-Version"
    private const val DEVICE_HEADER_KEY = "Device"
    private const val CONTENT_TYPE_HEADER_KEY = "Content-Type"
    private const val ACCEPT_HEADER_KEY = "Accept"
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"
    const val NO_HEADERS_MARKER = "NO_HEADERS"
  }
}