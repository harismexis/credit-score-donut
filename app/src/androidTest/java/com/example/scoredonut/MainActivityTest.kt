package com.example.scoredonut

import android.widget.ProgressBar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.scoredonut.mocks.MockProvider
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.ui.MainActivity
import com.example.scoredonut.util.getString
import com.example.scoredonut.util.getStringFormatted
import com.example.scoredonut.viewmodel.MainViewModel
import io.mockk.every
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.hamcrest.core.IsNot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    companion object {
        const val SCORE = 2
        const val MAX_SCORE = 10
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    lateinit var mockViewModel: MainViewModel

    @Before
    fun setup() {
        mockViewModel = MockProvider.provideMockViewModel()
        every { mockViewModel.bind() } returns Unit
        every { mockViewModel.unbind() } returns Unit
    }

    @Test
    fun viewModelUpdatesLiveData_UiIsUpdatedWithCreditScoreData() {
        // given
        val expectedHeader = getString(R.string.credit_score_header)
        val expectedScore = SCORE.toString()
        val expectedFooter = getStringFormatted(R.string.credit_score_footer, MAX_SCORE.toString())
        every { mockViewModel.creditUiModel } returns MockProvider.uiModel

        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            // when
            MockProvider.mUiModel.value = CreditUiModel(SCORE, MAX_SCORE)
        }

        // then
        onView(withId(R.id.loadingProgressBar)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.donutView)).check(matches(isDisplayed()))
        onView(withId(R.id.txtHeader)).check(matches(withText(expectedHeader)))
        onView(withId(R.id.txtCredit)).check(matches(withText(expectedScore)))
        onView(withId(R.id.txtFooter)).check(matches(withText(expectedFooter)))
        val scoreProgressBar = testRule.activity.findViewById<ProgressBar>(R.id.scoreProgressBar)
        assertEquals(MAX_SCORE, scoreProgressBar.max)
    }

    @Test
    fun viewModelDoesNotUpdateLiveData_loadingProgressShownAndDonutGone() {
        // given
        every { mockViewModel.creditUiModel } returns MockProvider.emptyUiModel

        // when
        testRule.launchActivity(null)

        // then
        onView(withId(R.id.loadingProgressBar)).check(matches((isDisplayed())))
        onView(withId(R.id.donutView)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtHeader)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtCredit)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtFooter)).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun activityPausesAndResumes_viewModelUnbindsAndBinds() {
        // given
        every { mockViewModel.creditUiModel } returns MockProvider.emptyUiModel
        testRule.launchActivity(null)

        pauseActivityAndVerifyViewModelUnbinds()
        resumeActivityAndVerifyViewModelBinds()
    }

    private fun pauseActivityAndVerifyViewModelUnbinds() {
        testRule.activity.runOnUiThread {
            // when
            InstrumentationRegistry.getInstrumentation().callActivityOnPause(testRule.activity)

            // then
            verify { mockViewModel.unbind() }
        }
    }

    private fun resumeActivityAndVerifyViewModelBinds() {
        testRule.activity.runOnUiThread {
            // when
            InstrumentationRegistry.getInstrumentation().callActivityOnResume(testRule.activity)

            // then
            verify { mockViewModel.bind() }
        }
    }

}
