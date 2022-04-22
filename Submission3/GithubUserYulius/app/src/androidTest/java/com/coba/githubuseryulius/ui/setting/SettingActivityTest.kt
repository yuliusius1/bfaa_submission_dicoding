package com.coba.githubuseryulius.ui.setting

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.coba.githubuseryulius.R
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test

class SettingsActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(SettingActivity::class.java)
    }

    private fun waitFor(delay: Long = 3000): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
    @Test
    fun testLight(){
        onView(withId(R.id.rb_light)).perform(click())
        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun testAuto(){
        onView(withId(R.id.rb_auto)).perform(click())
        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun testDark(){
        onView(withId(R.id.rb_dark)).perform(click())
        onView(isRoot()).perform(waitFor(1000))
    }
}