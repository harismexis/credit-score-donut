package com.example.scoredonut.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scoredonut.extensions.getErrorMessage
import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.BaseSchedulerProvider
import com.example.scoredonut.util.NetworkMonitor
import com.example.scoredonut.util.setSchedulersSingle
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var creditRepository: CreditRepository,
    var schedulerProvider: BaseSchedulerProvider,
    var networkMonitor: NetworkMonitor
) : ViewModel(), NetworkMonitor.NetworkMonitorClient {

    companion object {
        const val NULL_DATA = "Null data"
    }

    private var creditCallback: CreditResponseCallback? = null

    interface CreditResponseCallback {
        fun onCreditSuccess(uiModel: CreditUiModel)
        fun onCreditError(error: Throwable)
    }

    private var disposables: CompositeDisposable = CompositeDisposable()

    private val TAG = MainViewModel::class.qualifiedName

    init {
        networkMonitor.setClient(this)
    }

    private fun fetchCreditInfo() {
        disposables.add(creditRepository.getCredit()
            .compose(setSchedulersSingle(schedulerProvider))
            .doOnSuccess {
                onCreditInfoReceived(it)
            }
            .doOnError {
                Log.d(TAG, it.getErrorMessage())
                creditCallback?.onCreditError(it)
            }.subscribe()
        )
    }

    private fun onCreditInfoReceived(response: CreditResponse?) {
        response.toUiModel()?.also {
            creditCallback?.onCreditSuccess(it)
        } ?: run {
            creditCallback?.onCreditError(Throwable(NULL_DATA))
        }
    }

    fun startMonitor() {
        networkMonitor.startMonitor()
    }

    fun stopMonitor() {
        networkMonitor.stopMonitor()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        stopMonitor()
    }

    override fun onNetworkConnectionAvailable() {
        fetchCreditInfo()
    }

    override fun onNetworkConnectionLost() {
        // do nothing
    }

    fun setCreditResponseCallback(callback: CreditResponseCallback) {
        this.creditCallback = callback
    }
}