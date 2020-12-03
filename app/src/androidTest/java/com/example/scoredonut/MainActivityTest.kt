package com.example.scoredonut

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.scoredonut.mocks.MockMainViewModelProvider
import com.example.scoredonut.model.CreditUiModel
import com.example.scoredonut.ui.MainActivity
import io.mockk.verify
import org.hamcrest.core.IsNot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    companion object {
        const val SCORE = 80
        const val MAX_SCORE = 300
    }

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Test
    fun viewModelUpdatesLiveData_UiIsUpdatedWithCreditScoreData() {
        val expectedHeaderText = testRule.activity.getString(R.string.credit_score_header)
        val expectedScoreText = SCORE.toString()
        val expectedFooterText = testRule.activity.getString(R.string.credit_score_footer, MAX_SCORE)

        // when
        testRule.activity.runOnUiThread {
            MockMainViewModelProvider.mCreditUiModel.value = CreditUiModel(SCORE, MAX_SCORE)
        }

        // then
        onView(withId(R.id.loadingProgressBar)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.donutView)).check(matches(isDisplayed()))
        onView(withId(R.id.txtHeader)).check(matches(withText(expectedHeaderText)))
        onView(withId(R.id.txtCredit)).check(matches(withText(expectedScoreText)))
        onView(withId(R.id.txtFooter)).check(matches(withText(expectedFooterText)))
    }

    @Test
    fun viewModelDoesNotUpdateLiveData_loadingProgressBarIsShownAndDonutIsGone() {
        // then
        onView(withId(R.id.loadingProgressBar)).check(matches((isDisplayed())))
        onView(withId(R.id.donutView)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtHeader)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtCredit)).check(matches(IsNot(isDisplayed())))
        onView(withId(R.id.txtFooter)).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun activityPauses_activityUnbindsViewModel() {
        testRule.activity.runOnUiThread {
            InstrumentationRegistry.getInstrumentation().callActivityOnPause(testRule.activity)
            verify { MockMainViewModelProvider.mockViewModel.unbind() }
        }
    }


}
