package com.example.scoredonut.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.network.ConnectivityMonitor
import com.example.scoredonut.util.network.ConnectivityState
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.`when`

open class MainViewModelTestSetup {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @Mock
    lateinit var observer: Observer<CreditUiModel>

    protected  val connectivityUpdates: PublishRelay<ConnectivityState> =
        PublishRelay.create()

    protected  lateinit var mainViewModel: MainViewModel

    protected fun setupClassUnderTest() {
        mainViewModel = MainViewModel(mockCreditRepository, mockConnectivityMonitor)
        mainViewModel.creditUiModel.observeForever(observer)
    }

    protected fun setupMocks() {
        setupMockResponse()
        setupMockConnectivity()
        setupMockCreditRepository(mockResponse)
    }


    protected fun setupMockCreditRepository(creditResponse: CreditResponse) {
        runBlocking {
        `when`(mockCreditRepository.getCreditScore()).thenReturn(creditResponse)
        }
    }

    protected fun setupRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            // do nothing
        }
    }

    private fun setupMockConnectivity() {
        `when`(mockConnectivityMonitor.getConnectivityUpdates()).thenReturn(
            connectivityUpdates
        )
    }

    private fun setupMockResponse() {
        `when`(mockCreditInfo.score).thenReturn(SCORE)
        `when`(mockCreditInfo.maxScoreValue).thenReturn(MAX_SCORE)
        `when`(mockResponse.creditReportInfo).thenReturn(mockCreditInfo)
    }

}