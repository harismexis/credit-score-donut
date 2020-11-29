package com.example.scoredonut.viewmodel

import com.example.scoredonut.interfaces.CreditScoreCallback
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditScoreResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.network.ConnectivityMonitor
import com.example.scoredonut.util.network.ConnectivityState
import com.example.scoredonut.util.rx.schedulers.TrampolineSchedulerProvider
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import org.mockito.Mock
import org.mockito.Mockito.`when`

open class MainViewModelTestSetup {

    companion object {
        const val SCORE = 500
        const val MAX_SCORE = 700
    }

    @Mock
    protected lateinit var mockCreditRepository: CreditRepository

    @Mock
    protected lateinit var mockConnectivityMonitor: ConnectivityMonitor

    @Mock
    protected lateinit var mockCallback: CreditScoreCallback

    @Mock
    protected lateinit var mockResponse: CreditScoreResponse

    @Mock
    protected lateinit var mockCreditInfo: CreditReportInfo

    protected  var testSchedulerProvider = TrampolineSchedulerProvider()

    protected  lateinit var mainViewModel: MainViewModel

    protected  val connectivityUpdates: PublishRelay<ConnectivityState> =
        PublishRelay.create()

    protected fun initClassUnderTest() {
        mainViewModel =
            MainViewModel(mockCreditRepository, testSchedulerProvider, mockConnectivityMonitor)
        mainViewModel.setCreditResponseCallback(mockCallback)
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
        `when`(mockCreditRepository.getCreditScore()).thenReturn(
            Single.just(mockResponse)
        )
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