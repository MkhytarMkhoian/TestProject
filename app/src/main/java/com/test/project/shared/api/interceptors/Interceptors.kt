package com.test.project.shared.api.interceptors

import okhttp3.Interceptor

data class Interceptors(
  val items: List<Interceptor>
)