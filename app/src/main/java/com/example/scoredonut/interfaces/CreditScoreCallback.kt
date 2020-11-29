package com.example.scoredonut.interfaces

import com.example.scoredonut.model.CreditUiModel

interface CreditScoreCallback {
    fun onCreditScoreSuccess(uiModel: CreditUiModel)
    fun onCreditScoreError(error: Throwable)
}