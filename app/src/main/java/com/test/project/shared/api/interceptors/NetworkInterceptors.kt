package com.test.project.shared.api.interceptors

import okhttp3.Interceptor

data class NetworkInterceptors(
  val items: List<Interceptor>
)