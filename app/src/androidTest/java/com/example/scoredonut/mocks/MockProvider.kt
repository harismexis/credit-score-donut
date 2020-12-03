package com.example.scoredonut.mocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.viewmodel.MainViewModel
import io.mockk.mockk

object MockProvider {

    private var mockViewModel: MainViewModel = mockk(relaxed = true)

    var mEmptyUiModel = MutableLiveData<CreditUiModel>()
    val emptyUiModel: LiveData<CreditUiModel>
        get() = mEmptyUiModel

    var mUiModel = MutableLiveData<CreditUiModel>()
    val uiModel: LiveData<CreditUiModel>
        get() = mUiModel

    fun provideMockViewModel(): MainViewModel {
        return mockViewModel
    }
}