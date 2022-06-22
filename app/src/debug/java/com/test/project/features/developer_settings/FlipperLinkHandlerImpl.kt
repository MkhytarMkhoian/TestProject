package com.test.project.features.developer_settings

import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlipperLinkHandlerImpl @Inject constructor() : FlipperLinkHandler {

    override fun sendLink(url: String?) {
        url?.let { link ->
            NavigationFlipperPlugin.getInstance().sendNavigationEvent(link)
        }
    }
}
