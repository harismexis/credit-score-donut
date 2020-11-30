package com.example.scoredonut.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.example.scoredonut.util.network.ConnectivityMonitor
import com.example.scoredonut.util.network.ConnectivityRequestProvider
import com.example.scoredonut.util.network.ConnectivityState
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class ConnectivityMonitorTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockAppContext: Context

    @Mock
    lateinit var mockNetwork: Network

    @Mock
    lateinit var mockNetworkRequest: NetworkRequest

    @Mock
    lateinit var mockRequestProvider: ConnectivityRequestProvider

    @Mock
    lateinit var mockConnectivityManager: ConnectivityManager

    lateinit var connectivityMonitor: ConnectivityMonitor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupMocks()
        setupClassUnderTest()
    }

    private fun setupClassUnderTest() {
        connectivityMonitor = ConnectivityMonitor(mockContext, mockRequestProvider)
    }

    private fun setupMocks() {
        `when`(mockRequestProvider.provideNetworkRequest()).thenReturn(mockNetworkRequest)
        `when`(mockContext.applicationContext).thenReturn(mockAppContext)
        `when`(mockAppContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            mockConnectivityManager
        )
    }

    @Test
    fun networkMonitoringStarts_networkCallbackIsRegistered() {
        // when
        connectivityMonitor.startMonitor()

        // then
        verify(mockConnectivityManager, times(1))
            .registerNetworkCallback(mockNetworkRequest, connectivityMonitor)
    }

    @Test
    fun networkMonitoringStops_networkCallbackIsUnRegistered() {
        // when
        connectivityMonitor.stopMonitor()

        // then
        verify(mockConnectivityManager, times(1))
            .unregisterNetworkCallback(connectivityMonitor)
    }

    @Test
    fun networkIsAvailable_connectivityUpdatesEmitStateConnected() {
        // given
        val testObserver = connectivityMonitor.getConnectivityUpdates().test()

        // when
        connectivityMonitor.onAvailable(mockNetwork)

        // then
        testObserver.assertValue(ConnectivityState.CONNECTED)
    }

    @Test
    fun networkIsLost_connectivityUpdatesEmitStateDisconnected() {
        // given
        val testObserver = connectivityMonitor.getConnectivityUpdates().test()

        // when
        connectivityMonitor.onLost(mockNetwork)

        // then
        testObserver.assertValue(ConnectivityState.DISCONNECTED)
    }

}