package com.test.project.features.developer_settings

import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Interceptor
import javax.inject.Inject

class FlipperImpl @Inject constructor(
    private val plugin: NetworkFlipperPlugin,
    private val flipperOkhttpInterceptor: FlipperOkhttpInterceptor,
) : Flipper {

    override fun addNetworkFlipperPlugin(client: FlipperClient?) {
        client?.addPlugin(plugin)
    }

    override fun flipperOkhttpInterceptor(): Interceptor = flipperOkhttpInterceptor

}