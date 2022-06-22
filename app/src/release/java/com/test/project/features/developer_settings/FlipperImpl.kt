package com.test.project.features.developer_settings

import com.facebook.flipper.core.FlipperClient
import okhttp3.Interceptor
import javax.inject.Inject

class FlipperImpl @Inject constructor() : Flipper {
    override fun addNetworkFlipperPlugin(client: FlipperClient?) {
        TODO("Not yet implemented")
    }

    override fun flipperOkhttpInterceptor(): Interceptor? = null

}