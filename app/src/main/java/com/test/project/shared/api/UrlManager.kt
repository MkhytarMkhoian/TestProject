package com.test.project.shared.api

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import timber.log.Timber
import java.util.LinkedList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlManager
@Inject constructor(
) {

  fun getTokenBaseUrl(): String = runBlocking {
    return@runBlocking "dataStorage.getHost().url"
  }

  fun getApiBaseUrl(): String = runBlocking {
    return@runBlocking "dataStorage.getHost().url"
  }

  fun concatWithBaseUrl(method: String): String {
    return getApiBaseUrl() + method
  }

  fun builder(): UrlBuilder {
    return UrlBuilder(this)
  }

  /**
   * This method is used as a fix for pagination issue.
   * When you query url with HTTPS scheme, endpoint could return meta links
   * with `HTTP` scheme. HTTP scheme is not supported by v3 backend.
   *
   *
   * So this is a temporary fir for this issue.
   *
   * @param url valid url with `HTTP` or `HTTPS` scheme.
   * @return fixed url
   */
  fun fixHttpsIfNeeded(url: String): String {
    val parsedUrl = url.toHttpUrlOrNull()
    if (parsedUrl == null) {
      Timber.e("URL '%s' is not valid url", url)
      return url
    }
    return if (parsedUrl.isHttps) {
      url
    } else parsedUrl.newBuilder()
      .scheme("https")
      .build()
      .toString()
  }

  class UrlBuilder @VisibleForTesting constructor(urlManager: UrlManager) {
    private val queryMap: MutableMap<String, String>
    private val pathSegments: MutableList<String>
    private val pathsSegments: MutableList<String>
    private val urlManager: UrlManager

    fun addQueryParam(key: String, value: Int): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addQueryParam(key: String, value: Long): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addQueryParam(key: String, value: String): UrlBuilder {
      queryMap[key] = value
      return this
    }

    fun addQueryParam(key: String, value: Double): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addQueryParam(key: String, value: Float): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addQueryParam(key: String, value: Short): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addQueryParam(key: String, value: Char): UrlBuilder {
      queryMap[key] = value.toString()
      return this
    }

    fun addPathSegment(path: String): UrlBuilder {
      pathSegments.add(path)
      return this
    }

    fun addPathSegments(pathSegments: String): UrlBuilder {
      pathsSegments.add(pathSegments)
      return this
    }

    operator fun set(key: String, value: Any) {
      when (value) {
        is Int -> addQueryParam(key, value)
        is Short -> addQueryParam(key, value)
        is String -> addQueryParam(key, value)
        is Long -> addQueryParam(key, value)
        is Double -> addQueryParam(key, value)
        is Float -> addQueryParam(key, value)
        is Char -> addQueryParam(key, value)
        else -> addQueryParam(key, value.toString())
      }
    }

    fun build(): String? {
      var bailedUrlString: String? = null
      bailedUrlString = try {
        val parseUrl = urlManager.getApiBaseUrl().toHttpUrl()
        val builder = parseUrl.newBuilder()

        for (segments in pathsSegments) {
          builder.addPathSegments(segments)
        }

        for (segment in pathSegments) {
          builder.addPathSegment(segment)
        }

        for (key in queryMap.keys) {
          builder.addQueryParameter(key, queryMap[key])
        }

        val buildUrl: HttpUrl = builder.build()
        buildUrl.toString()

      } finally {
        if (bailedUrlString == null) {
          val lastPath: String?
          if (pathSegments.isNotEmpty()) {
            lastPath = pathSegments[pathSegments.size - 1]
          } else {
            lastPath = null
          }
          Timber.e("Url is not build properly for method '%s", lastPath)
        }
      }
      return bailedUrlString
    }

    init {
      pathSegments = LinkedList()
      pathsSegments = LinkedList()
      this.urlManager = urlManager
      queryMap = HashMap()
    }
  }
}