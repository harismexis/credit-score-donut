package com.example.scoredonut.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scoredonut.extensions.getErrorMessage
import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.interfaces.CreditScoreCallback
import com.example.scoredonut.model.CreditScoreResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.BaseSchedulerProvider
import com.example.scoredonut.util.ConnectivityMonitor
import com.example.scoredonut.util.ConnectivityState
import com.example.scoredonut.util.setSchedulersSingle
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var creditRepository: CreditRepository,
    var schedulerProvider: BaseSchedulerProvider,
    var connectivity: ConnectivityMonitor
) : ViewModel() {

    private var creditScoreCallback: CreditScoreCallback? = null
    private var disposables: CompositeDisposable = CompositeDisposable()
    private val TAG = MainViewModel::class.qualifiedName

    override fun onCleared() {
        super.onCleared()
        unbind()
    }

    fun bind() {
        disposables = CompositeDisposable()
        disposables.addAll(getConnectivityUpdates())
        connectivity.startMonitor()
    }

    fun unbind() {
        disposables.dispose()
        connectivity.stopMonitor()
    }

    fun setCreditResponseCallback(callback: CreditScoreCallback) {
        this.creditScoreCallback = callback
    }

    private fun getConnectivityUpdates(): Disposable {
        return connectivity.getConnectivityUpdates()
            .filter { it == ConnectivityState.CONNECTED }
            .flatMapSingle { getCreditScore() }
            .doOnError {
                Log.d(TAG, it.getErrorMessage())
                creditScoreCallback?.onCreditScoreError()
            }
            .subscribe()
    }

    private fun getCreditScore(): Single<CreditScoreResponse?> {
        return creditRepository.getCreditScore()
            .compose(setSchedulersSingle(schedulerProvider))
            .doOnSuccess {
                it.toUiModel()?.let { uiModel ->
                    creditScoreCallback?.onCreditScoreSuccess(uiModel)
                }
            }
    }

}