package com.example.scoredonut.viewmodel

import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.util.network.ConnectivityState
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
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
        verify(mockCallback, times(1)).onCreditScoreSuccess(mockResponse.toUiModel())
        verify(mockCallback, never()).onCreditScoreError()
        Assert.assertEquals(SCORE, mockResponse.toUiModel().score)
        Assert.assertEquals(MAX_SCORE, mockResponse.toUiModel().maxScoreValue)
    }

    @Test
    fun networkIsOnAndCreditCallThrowsError_onErrorCallbackCalledWithThrownError() {
        // given
        val error = IllegalStateException("Illegal State Exception")
        `when`(mockCreditRepository.getCreditScore()).thenThrow(error)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallTriggeredErrorCallback()
    }

    @Test
    fun networkIsOnAndResponseHasNoReportInfo_onErrorCallbackCalledWithError() {
        // given
        `when`(mockResponse.creditReportInfo).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallTriggeredErrorCallback()
    }

    @Test
    fun networkIsOnAndResponseHasNoScore_onErrorCallbackCalledWithError() {
        // given
        `when`(mockCreditInfo.score).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallTriggeredErrorCallback()
    }

    @Test
    fun networkIsOnAndResponseHasNoMaxScore_onErrorCallbackCalledWithError() {
        // given
        `when`(mockCreditInfo.maxScoreValue).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyNetworkCallTriggeredErrorCallback()
    }

    private fun triggerCreditScoreRequest() {
        mainViewModel.bind()
        connectivityUpdates.accept(ConnectivityState.CONNECTED)
    }

    private fun verifyNetworkCallTriggeredErrorCallback() {
        verify(mockCreditRepository, times(1)).getCreditScore()
        verify(mockCallback, never()).onCreditScoreSuccess(com.nhaarman.mockitokotlin2.any())
        verify(mockCallback).onCreditScoreError()
    }

}