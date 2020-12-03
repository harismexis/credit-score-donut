package com.example.scoredonut.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.scoredonut.R
import com.example.scoredonut.databinding.ActivityMainBinding
import com.example.scoredonut.databinding.DonutViewBinding
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var donutBinding: DonutViewBinding

    private var handler = Handler(Looper.getMainLooper())
    private var animationThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initialiseViewBinding()
        setContentView(binding.root)
        initialiseViewModel()
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.bind()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unbind()
        animationThread?.interrupt()
    }

    private fun observeLiveData() {
        viewModel.creditUiModel.observe(this, {
            updateCreditScoreView(it)
        })
    }

    private fun initialiseViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        donutBinding = binding.donutView
    }

    private fun initialiseViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun updateCreditScoreView(uiModel: CreditUiModel) {
        donutBinding.scoreProgressBar.max = uiModel.maxScoreValue
        donutBinding.txtFooter.text = getString(
            R.string.credit_score_footer,
            uiModel.maxScoreValue.toString()
        )
        donutBinding.root.visibility = View.VISIBLE
        binding.loadingProgressBar.visibility = View.GONE
        animateScore(uiModel.score)
    }

    private fun animateScore(userScore: Int) {
        animationThread = Thread {
            for (progress in 1..userScore) {
                handler.post {
                    donutBinding.scoreProgressBar.progress = progress
                    donutBinding.txtCredit.text = progress.toString()
                }
                try {
                    Thread.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.apply { start() }
    }

}