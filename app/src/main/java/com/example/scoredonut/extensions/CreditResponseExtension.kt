package com.example.scoredonut.extensions

import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.model.CreditUiModel

fun CreditResponse?.toUiModel(): CreditUiModel? {
    this?.let {
        it.creditReportInfo?.let { info ->
            info.score?.let { score ->
                info.maxScoreValue?.let { maxScore ->
                    return CreditUiModel(score, maxScore)
                }
            }
        }
    }
    return null
}