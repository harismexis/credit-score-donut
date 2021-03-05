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
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var donutBinding: DonutViewBinding

    private var handler = Handler(Looper.getMainLooper())
    private var donutThread: Thread? = null
    private val isDonutThreadRunning: AtomicBoolean = AtomicBoolean(false)

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
        stopDonutProgress()
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

    private fun stopDonutProgress() {
        isDonutThreadRunning.set(false)
        donutThread?.interrupt()
    }

    private fun updateDonutProgress(progress: Int) {
        donutBinding.scoreProgressBar.progress = progress
        donutBinding.txtCredit.text = progress.toString()
    }

    private fun animateScore(userScore: Int) {
        donutThread = Thread {
            var progress = 0
            isDonutThreadRunning.set(true)
            while (isDonutThreadRunning.get() && progress <= userScore) {
                handler.post { updateDonutProgress(progress) }
                try {
                    Thread.sleep(5)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    e.printStackTrace()
                }
                progress++
            }
        }.apply { start() }
    }

}