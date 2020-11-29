package com.example.scoredonut.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.scoredonut.R
import com.example.scoredonut.databinding.ActivityMainBinding
import com.example.scoredonut.interfaces.CreditScoreCallback
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CreditScoreCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initialiseViewBinding()
        setContentView(binding.root)
        initialiseViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.bind()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unbind()
    }

    override fun onCreditScoreSuccess(uiModel: CreditUiModel) {
        updateCreditScoreView(uiModel)
    }

    override fun onCreditScoreError(error: Throwable) {
        Toast.makeText(
            this,
            error.message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initialiseViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun initialiseViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.setCreditResponseCallback(this)
    }

    private fun updateCreditScoreView(uiModel: CreditUiModel) {
        val donutView = binding.donutView
        donutView.progressBar.max = uiModel.maxScoreValue
        donutView.progressBar.progress = uiModel.score
        donutView.txtCredit.text = uiModel.score.toString()
        donutView.txtFooter.text = getString(
            R.string.credit_score_footer,
            uiModel.maxScoreValue.toString()
        )
        donutView.root.visibility = View.VISIBLE
    }

}