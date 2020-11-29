package com.example.scoredonut.extensions

import com.example.scoredonut.model.CreditScoreResponse
import com.example.scoredonut.model.CreditUiModel

fun CreditScoreResponse?.toUiModel(): CreditUiModel? {
    this?.let {
        it.creditReportInfo?.let { info ->
            info.score?.let { score ->
                info.maxScoreValue?.let { maxScore ->
                    return CreditUiModel(score, maxScore)
                }
                throw IllegalStateException("Null maxScoreValue")
            }
            throw IllegalStateException("Null score")
        }
        throw IllegalStateException("Null creditReportInfo")
    }
    throw IllegalStateException("Null response")
}

fun CreditScoreResponse?.checkFields() {
    if (this == null) throw IllegalStateException("Null response")
    if (this.creditReportInfo == null) throw IllegalStateException("Null creditReportInfo")
    if (this.creditReportInfo?.score == null) throw IllegalStateException("Null score")
    if (this.creditReportInfo?.maxScoreValue == null) throw IllegalStateException("Null maxScoreValue")
}