package com.example.scoredonut.extensions

import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.utils.getFakeCreditResponseNoCreditReportInfo
import com.example.scoredonut.utils.getFakeCreditResponseNoMaxScore
import com.example.scoredonut.utils.getFakeCreditResponseNoScore
import com.example.scoredonut.utils.getFakeCreditResponseValid
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreditResponseExtensionTest {

    companion object {
        const val FAKE_SCORE = 300
        const val FAKE_MAX_SCORE = 500
        const val FAKE_SCORE_IN_FAKE_RESPONSE = 327
        const val FAKE_MAX_SCORE_IN_FAKE_RESPONSE = 700
    }

    @Test
    fun responseHasAllData_uiModelHasExpectedData() {
        // given
        val info = CreditReportInfo(FAKE_SCORE, FAKE_MAX_SCORE)
        val response = CreditResponse(info)

        // when
        val uiModel = response.toUiModel()

        // then
        Assert.assertEquals(FAKE_SCORE, uiModel.score)
        Assert.assertEquals(FAKE_MAX_SCORE, uiModel.maxScoreValue)
    }

    @Test
    fun responseFromStringHasAllData_uiModelHasExpectedData() {
        // given
        val response = getFakeCreditResponseValid()

        // when
        val uiModel = response.toUiModel()

        // then
        Assert.assertEquals(FAKE_SCORE_IN_FAKE_RESPONSE, uiModel.score)
        Assert.assertEquals(FAKE_MAX_SCORE_IN_FAKE_RESPONSE, uiModel.maxScoreValue)
    }

    @Test(expected = IllegalStateException::class)
    fun responseHasNullScore_conversionToUiModelThrowsException() {
        // given
        val info = CreditReportInfo(null, FAKE_MAX_SCORE)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseFromStringHasNoScore_conversionToUiModelThrowsException() {
        // given
        val response = getFakeCreditResponseNoScore()

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseHasNullMaxScore_conversionToUiModelThrowsException() {
        // given
        val info = CreditReportInfo(FAKE_SCORE, null)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseFromStringHasNoMaxScore_conversionToUiModelThrowsException() {
        // given
        val response = getFakeCreditResponseNoMaxScore()

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseHasNullValues_conversionToUiModelThrowsException() {
        // given
        val info = CreditReportInfo(null, null)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseHasNullCreditReportInfo_conversionToUiModelThrowsException() {
        // given
        val response = CreditResponse(null)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseFromStringHasNoCreditReportInfo_conversionToUiModelThrowsException() {
        // given
        val response = getFakeCreditResponseNoCreditReportInfo()

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseIsNull_conversionToUiModelThrowsException() {
        // given
        val response = null

        // when
        response.toUiModel()
    }
}