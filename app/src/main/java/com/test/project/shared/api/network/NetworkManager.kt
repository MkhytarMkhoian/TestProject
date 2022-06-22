package com.test.project.shared.api.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

interface NetworkManager {
  fun isOnline(): Boolean
}

@Singleton
class NetworkManagerImpl
@Inject constructor(
  private val context: Context
) : NetworkManager {

  private val networks: MutableList<Network> = mutableListOf()

  init {
    // Android 10+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      val cm = getManager()

      // request all networks that able to reach internet
      val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

      cm?.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
          super.onAvailable(network)
          networks.add(network)
        }

        override fun onLost(network: Network) {
          super.onLost(network)
          networks.remove(network)
        }

        override fun onUnavailable() {
          super.onUnavailable()
          networks.clear()
        }
      })
    }
  }

  override fun isOnline() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    @Suppress("DEPRECATION")
    getManager()?.activeNetworkInfo?.isConnected ?: false
  } else {
    networks.isNotEmpty()
  }

  private fun getManager() =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

}
