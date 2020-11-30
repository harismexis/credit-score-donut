package com.example.scoredonut.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.network.ConnectivityMonitor
import com.example.scoredonut.util.network.ConnectivityState
import com.example.scoredonut.utils.MainCoroutineScopeRule
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.`when`

open class MainViewModelTestSetup {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    companion object {
        const val SCORE = 500
        const val MAX_SCORE = 700
    }

    @Mock
    protected lateinit var mockCreditRepository: CreditRepository

    @Mock
    protected lateinit var mockConnectivityMonitor: ConnectivityMonitor

    @Mock
    protected lateinit var mockResponse: CreditResponse

    @Mock
    protected lateinit var mockCreditInfo: CreditReportInfo

    protected  lateinit var mainViewModel: MainViewModel

    protected  val connectivityUpdates: PublishRelay<ConnectivityState> =
        PublishRelay.create()

    protected fun initClassUnderTest() {
        mainViewModel = MainViewModel(mockCreditRepository, mockConnectivityMonitor)
    }

    protected fun setupMocks() {
        setupMockResponse()
        setupMockConnectivity()
        setupMockCreditScoreRepository()
    }

    protected fun setupMockResponse() {
        `when`(mockCreditInfo.score).thenReturn(SCORE)
        `when`(mockCreditInfo.maxScoreValue).thenReturn(MAX_SCORE)
        `when`(mockResponse.creditReportInfo).thenReturn(mockCreditInfo)
    }

    protected fun setupMockCreditScoreRepository() {
        runBlocking {
        `when`(mockCreditRepository.getCreditScore()).thenReturn(mockResponse)
        }
    }

    protected fun setupMockConnectivity() {
        `when`(mockConnectivityMonitor.getConnectivityUpdates()).thenReturn(
            connectivityUpdates
        )
    }

    protected fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            // do nothing
        }
    }
}