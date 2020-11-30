package com.example.extensions

import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreditResponseExtensionTest {

    companion object {
        const val SCORE = 300
        const val MAX_SCORE = 500
    }

    @Test
    fun responseHasAllData_uiModelHasExpectedData() {
        // given
        val info = CreditReportInfo(SCORE, MAX_SCORE)
        val response = CreditResponse(info)

        // when
        val uiModel = response.toUiModel()

        // then
        Assert.assertEquals(SCORE, uiModel.score)
        Assert.assertEquals(MAX_SCORE, uiModel.maxScoreValue)
    }

    @Test(expected = IllegalStateException::class)
    fun responseMissingScore_exceptionIsThrown() {
        // given
        val info = CreditReportInfo(null, MAX_SCORE)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseMissingMaxScore_exceptionIsThrown() {
        // given
        val info = CreditReportInfo(SCORE, null)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseMissingScoreValues_exceptionIsThrown() {
        // given
        val info = CreditReportInfo(null, null)
        val response = CreditResponse(info)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseMissingReportInfo_exceptionIsThrown() {
        // given
        val response = CreditResponse(null)

        // when
        response.toUiModel()
    }

    @Test(expected = IllegalStateException::class)
    fun responseIsNull_exceptionIsThrown() {
        // given
        val response = null

        // when
        response.toUiModel()
    }
}