package com.example.scoredonut.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
    var appContext: Context
) : NetworkCallback() {

    private var client: NetworkMonitorClient? = null

    interface NetworkMonitorClient {
        fun onNetworkConnectionAvailable()
        fun onNetworkConnectionLost()
    }

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    fun startMonitor() {
        val connectivityManager = appContext.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun stopMonitor() {
        val connectivityManager = appContext.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        client?.onNetworkConnectionAvailable()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        client?.onNetworkConnectionLost()
    }

    fun setClient(client: NetworkMonitorClient) {
        this.client = client
    }
}