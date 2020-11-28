package com.example.scoredonut.viewmodel

import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.NetworkMonitor
import com.example.scoredonut.util.TrampolineSchedulerProvider
import com.example.scoredonut.viewmodel.MainViewModel.CreditResponseCallback
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Mock
    lateinit var mockCreditRepository: CreditRepository

    @Mock
    lateinit var mockNetworkMonitor: NetworkMonitor

    @Mock
    lateinit var mockCreditCallback: CreditResponseCallback

    private var fakeCreditResponse: CreditResponse? = null

    private var testSchedulerProvider = TrampolineSchedulerProvider()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        initFakes()
        setupMocks()
        initClassUnderTest()
        initRxErrorHandler()
    }

    private fun initClassUnderTest() {
        mainViewModel =
            MainViewModel(mockCreditRepository, testSchedulerProvider, mockNetworkMonitor)
        mainViewModel.setCreditResponseCallback(mockCreditCallback)
    }

    private fun initFakes() {
        fakeCreditResponse = CreditResponse(CreditReportInfo(300, 500))
    }

    private fun setupMocks() {
        `when`(mockCreditRepository.getCredit()).thenReturn(
            Single.just(fakeCreditResponse)
        )
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { ex: Throwable ->

        }
    }

    @Test
    fun networkIsOnAndCreditResponseIsValid_networkCallDoneAndSuccessCallbackCalled() {
        // when
        mainViewModel.onNetworkConnectionAvailable()

        // then
        verify(mockCreditRepository, times(1)).getCredit()
        verify(mockCreditCallback, times(1)).onCreditSuccess(fakeCreditResponse.toUiModel()!!)
    }

//    @Test
//    fun networkIsOnAndCreditResponseIsNull_networkCallDoneAndErrorCallbackCalled() {
//        // given
////        `when`(mockCreditRepository.getCredit()).thenReturn(
////            Single.just(null)
////        )
//
//        // when
//        mainViewModel.onNetworkConnectionAvailable()
//
//        // then
//        verify(mockCreditRepository, times(1)).getCredit()
//        verify(mockCreditCallback, times(1))
//            .onCreditError(Throwable(NULL_DATA))
//    }


    @Test(expected = IllegalStateException::class)
    fun networkIsOnAndCreditCallThrowsError_onErrorCallbackCalledWithThrownError() {
        // given
        val error = IllegalStateException("Illegal State Exception")
        `when`(mockCreditRepository.getCredit()).thenThrow(error)


        // when
        mainViewModel.onNetworkConnectionAvailable()

        // then
        verify(mockCreditRepository, times(1)).getCredit()
        verify(mockCreditCallback, times(1)).onCreditError(error)
    }

}