package com.example.scoredonut.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.jakewharton.rxrelay2.PublishRelay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityMonitor @Inject constructor(
    var appContext: Context
) : NetworkCallback() {

    private val connectivityUpdates: PublishRelay<ConnectivityState> =
        PublishRelay.create()

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
        connectivityUpdates.accept(ConnectivityState.CONNECTED)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        connectivityUpdates.accept(ConnectivityState.DISCONNECTED)
    }

    fun getConnectivityUpdates(): PublishRelay<ConnectivityState> {
        return connectivityUpdates
    }

}