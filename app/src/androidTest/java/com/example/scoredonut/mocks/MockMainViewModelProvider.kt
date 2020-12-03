package com.example.scoredonut.mocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.viewmodel.MainViewModel
import io.mockk.every
import io.mockk.mockk

object MockMainViewModelProvider {

    var mockViewModel: MainViewModel = mockk(relaxed = true)

    val mCreditUiModel = MutableLiveData<CreditUiModel>()

    private val creditUiModel: LiveData<CreditUiModel>
        get() = mCreditUiModel

    init {
        every { mockViewModel.creditUiModel } returns creditUiModel
    }
}