package com.example.scoredonut.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.scoredonut.R
import com.example.scoredonut.util.NetworkConnectionListener
import com.example.scoredonut.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NetworkConnectionListener.NetworkConnectionClient {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private var networkListener: NetworkConnectionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        networkListener = NetworkConnectionListener(this)
    }

    override fun onResume() {
        super.onResume()
        networkListener?.startListen()
    }

    override fun onPause() {
        super.onPause()
        networkListener?.stopListen()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkListener = null
    }

    override fun onNetworkConnectionAvailable() {
        //
    }

    override fun onNetworkConnectionLost() {
        //
    }

}