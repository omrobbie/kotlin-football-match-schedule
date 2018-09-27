package com.omrobbie.footballmatchschedule.mvp.match

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.omrobbie.footballmatchschedule.R
import junit.framework.AssertionFailedError
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class MatchActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchActivity::class.java)

    @Test
    fun showDetailsFromRecyclerView() {
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(1000)
    }

    @Test
    fun playWithBottomNavigationView() {
        Thread.sleep(1000)
        onView(withId(R.id.bnv_favorites)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.bnv_match_next)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.bnv_match_prev)).perform(click())

        Thread.sleep(1000)
    }

    @Test
    fun addFavorites() {
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())

        Thread.sleep(1000)
        onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(1000)
        onView(withText("Barcelona")).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()

        Thread.sleep(1000)
        onView(withId(R.id.bnv_favorites)).perform(click())

        Thread.sleep(1000)
    }

    @Test
    fun removeFavorites() {
        onView(withId(R.id.bnv_favorites)).perform(click())

        Thread.sleep(1000)
        try {
            onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        } catch (e: AssertionFailedError) {
            return
        }

        Thread.sleep(1000)
        onView(withText("Barcelona")).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.mn_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.mn_favorites)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()

        Thread.sleep(1000)
    }
}
