package com.example.scoredonut.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoredonut.model.CreditReportInfo
import com.example.scoredonut.model.CreditResponse
import com.example.scoredonut.repository.CreditRepository
import com.example.scoredonut.util.TrampolineSchedulerProvider
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {

    companion object {
        const val EXPECTED_NUMBER_OF_ITEMS = 32
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockCreditRepository: CreditRepository

    @Mock
    lateinit var mockCreditReportInfo: CreditReportInfo

    private lateinit var mainViewModel: MainViewModel

    private var testSchedulerProvider = TrampolineSchedulerProvider()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupMocks()
        initClassUnderTest()
    }

    private fun initClassUnderTest() {
        mainViewModel = MainViewModel(mockCreditRepository, testSchedulerProvider)
    }

    private fun setupMocks() {
        `when`(mockCreditRepository.getCredit()).thenReturn(
            Single.just(CreditResponse(CreditReportInfo(1.0f, 2.0f)))
        )
    }

    @Test
    fun rateUpdateStartsAndStops_firstUiModelIsTheExpectedFirstResponder() {
        // given

        // when

        // then
        verify(mockCreditRepository, times(1)).getCredit()
    }

}