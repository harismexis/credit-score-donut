package com.example.scoredonut.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scoredonut.util.BaseSchedulerProvider
import com.example.scoredonut.extensions.getErrorMessage
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.setSchedulersSingle
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var creditRepository: CreditRepository,
    var schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    private var disposables: CompositeDisposable? = null

    companion object {
        private const val FIRST_RESPONDER_INITIAL_INPUT = "1"
        private const val ZERO_AMOUNT = 0.0f
    }

    private val TAG = MainViewModel::class.qualifiedName

    init {

    }

    private fun getCredit(): Single<CreditResponse?> {
        return creditRepository.getCredit()
            .compose(setSchedulersSingle(schedulerProvider))
            .doOnSuccess {

            }
            .doOnError {
                Log.d(TAG, it.getErrorMessage())
            }
    }

}