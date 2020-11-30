package com.example.scoredonut.viewmodel

import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.util.network.ConnectivityState
import com.example.scoredonut.utils.getFakeCreditResponseNoCreditReportInfo
import com.example.scoredonut.utils.getFakeCreditResponseNoMaxScore
import com.example.scoredonut.utils.getFakeCreditResponseNoScore
import com.example.scoredonut.utils.getFakeCreditResponseValid
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
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
        setupClassUnderTest()
        setupRxErrorHandler()
    }

    @Test
    fun creditResponseIsValid_networkCallDoneAndDataUpdated() {
        // given
        val uiModel = mockResponse.toUiModel()

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataUpdated(mockResponse, uiModel)
    }

    @Test
    fun creditResponseParsedFromStringIsValid_networkCallDoneAndDataUpdated() {
        // given
        val fakeResponse = getFakeCreditResponseValid()
        setupMockCreditRepository(fakeResponse)
        val uiModel = fakeResponse.toUiModel()

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataUpdated(fakeResponse, uiModel)
    }

    @Test
    fun creditResponseHasNullScore_dataIsNotUpdated() {
        // given
        `when`(mockCreditInfo.score).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditResponseFromStringHasNoScore_dataIsNotUpdated() {
        // given
        setupMockCreditRepository(getFakeCreditResponseNoScore())

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditResponseHasNullMaxScore_dataIsNotUpdated() {
        // given
        `when`(mockCreditInfo.maxScoreValue).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditResponseFromStringHasNoMaxScore_dataIsNotUpdated() {
        // given
        setupMockCreditRepository(getFakeCreditResponseNoMaxScore())

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditResponseHasNullCreditReportInfo_dataIsNotUpdated() {
        // given
        `when`(mockResponse.creditReportInfo).thenReturn(null)

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditResponseFromStringHasNoCreditReportInfo_dataIsNotUpdated() {
        // given
        setupMockCreditRepository(getFakeCreditResponseNoCreditReportInfo())

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    @Test
    fun creditCallThrowsError_dataIsNotUpdated() {
        // given
        val error = IllegalStateException("Illegal State Exception")
        runBlocking {
            `when`(mockCreditRepository.getCreditScore()).thenThrow(error)
        }

        // when
        triggerCreditScoreRequest()

        // then
        verifyCreditCallDoneAndDataNotUpdated()
    }

    private fun triggerCreditScoreRequest() {
        mainViewModel.bind()
        connectivityUpdates.accept(ConnectivityState.CONNECTED)
    }

    private fun verifyCreditCallDoneAndDataNotUpdated() {
        runBlocking {
            verify(mockCreditRepository, times(1)).getCreditScore()
        }
        verify(observer, never()).onChanged(any())
    }

    private fun verifyCreditCallDoneAndDataUpdated(
        response: CreditResponse?,
        uiModel: CreditUiModel?
    ) {
        runBlocking {
            verify(mockCreditRepository, times(1)).getCreditScore()
        }
        verify(observer).onChanged(uiModel)
        verifyUiModelValues(response, uiModel)
        verifyLiveDataValues(response)
    }

    private fun verifyUiModelValues(
        response: CreditResponse?,
        uiModel: CreditUiModel?
    ) {
        Assert.assertEquals(response!!.creditReportInfo!!.score, uiModel!!.score)
        Assert.assertEquals(response.creditReportInfo!!.maxScoreValue, uiModel.maxScoreValue)
    }

    private fun verifyLiveDataValues(
        response: CreditResponse?
    ) {
        Assert.assertEquals(
            response!!.creditReportInfo!!.score,
            mainViewModel.creditUiModel.value!!.score
        )
        Assert.assertEquals(
            response.creditReportInfo!!.maxScoreValue,
            mainViewModel.creditUiModel.value!!.maxScoreValue
        )
    }

}