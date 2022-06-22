package com.test.project.features.developer_settings

import com.facebook.flipper.core.FlipperClient
import okhttp3.Interceptor

interface Flipper {
    fun addNetworkFlipperPlugin(client: FlipperClient?)
    fun flipperOkhttpInterceptor(): Interceptor?
}