package com.example.scoredonut.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoredonut.extensions.getErrorMessage
import com.example.scoredonut.extensions.toUiModel
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.network.ConnectivityMonitor
import com.example.scoredonut.util.network.ConnectivityState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var creditRepository: CreditRepository,
    var connectivity: ConnectivityMonitor,
) : ViewModel() {

    private var disposables: CompositeDisposable = CompositeDisposable()
    private val TAG = MainViewModel::class.qualifiedName
    private var jobGetCredit: Job? = null

    private val mCreditUiModel = MutableLiveData<CreditUiModel>()
    val creditUiModel: LiveData<CreditUiModel>
        get() = mCreditUiModel

    override fun onCleared() {
        super.onCleared()
        jobGetCredit?.cancel()
        jobGetCredit = null
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

    private fun getConnectivityUpdates(): Disposable {
        return connectivity.getConnectivityUpdates()
            .filter { it == ConnectivityState.CONNECTED }
            .doOnNext {
                jobGetCredit = retrieveCreditScore()
            }
            .doOnError {
                Log.d(TAG, it.getErrorMessage())
            }
            .subscribe()
    }

    private fun retrieveCreditScore(): Job {
        return viewModelScope.launch {
            try {
                val response = creditRepository.getCreditScore()
                mCreditUiModel.value = response.toUiModel()
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
            }
        }
    }

}