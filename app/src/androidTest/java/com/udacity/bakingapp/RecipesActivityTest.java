package com.udacity.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.activities.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void scrollToItem_checkItsText() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.recyclerView_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        // Match the text in an item and check that it's displayed.
        String itemElementText = "Brownies";

        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerView_checkItsEnabled() {
        onView(withId(R.id.recyclerView_recipes))
                .perform(click())
                .check(matches(isEnabled()));
    }

}