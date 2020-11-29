package com.example.scoredonut.viewmodel

import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.util.ConnectivityState
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest : MainViewModelTestSetup() {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupMocks()
        initClassUnderTest()
        initRxErrorHandler()
    }

    @Test
    fun networkIsOnAndCreditResponseIsValid_networkCallDoneAndSuccessCallbackCalled() {
        // when
        triggerCreditScoreRequest()

        // then
        verify(mockCreditRepository, times(1)).getCreditScore()
        verify(mockCallback, times(1)).onCreditScoreSuccess(mockResponse.toUiModel()!!)
        verify(mockCallback, never()).onCreditScoreError(com.nhaarman.mockitokotlin2.any())
        Assert.assertEquals(SCORE, mockResponse.toUiModel()!!.score)
        Assert.assertEquals(MAX_SCORE, mockResponse.toUiModel()!!.maxScoreValue)
    }

    @Test
    fun networkIsOnAndCreditCallThrowsError_onErrorCallbackCalledWithThrownError() {
        // given
        val error = IllegalStateException("Illegal State Exception")
        `when`(mockCreditRepository.getCreditScore()).thenThrow(error)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallDoneAndOnSuccessNeverCalled()
        verify(mockCallback, times(1)).onCreditScoreError(error)
    }

    @Test
    fun networkIsOnAndResponseHasNoReportInfo_onErrorCallbackCalledWithError() {
        // given
        `when`(mockResponse.creditReportInfo).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallDoneAndOnSuccessNeverCalled()

        val captor = argumentCaptor<Throwable>()
        verify(mockCallback).onCreditScoreError(captor.capture())
        assert(captor.firstValue.message == "Null creditReportInfo")
    }

    @Test
    fun networkIsOnAndResponseHasNoScore_onErrorCallbackCalledWithError() {
        // given
        `when`(mockCreditInfo.score).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallDoneAndOnSuccessNeverCalled()
        verifyErrorCallbackCalledWithError("Null score")
    }

    @Test
    fun networkIsOnAndResponseHasNoMaxScore_onErrorCallbackCalledWithError() {
        // given
        `when`(mockCreditInfo.maxScoreValue).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallDoneAndOnSuccessNeverCalled()
        verifyErrorCallbackCalledWithError("Null maxScoreValue")
    }

    private fun verifyErrorCallbackCalledWithError(errorMsg: String) {
        val captor = argumentCaptor<Throwable>()
        verify(mockCallback).onCreditScoreError(captor.capture())
        assert(captor.firstValue.message == errorMsg)
    }

    private fun triggerCreditScoreRequest() {
        mainViewModel.bind()
        connectivityUpdates.accept(ConnectivityState.CONNECTED)
    }

    private fun verifyNetworkCallDoneAndOnSuccessNeverCalled() {
        verify(mockCreditRepository, Mockito.times(1)).getCreditScore()
        verify(mockCallback, Mockito.never()).onCreditScoreSuccess(com.nhaarman.mockitokotlin2.any())
    }

}