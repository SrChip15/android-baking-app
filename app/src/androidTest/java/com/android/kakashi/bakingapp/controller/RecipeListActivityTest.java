package com.android.kakashi.bakingapp.controller;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.kakashi.bakingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityTest {

	@Rule
	public ActivityTestRule<RecipeListActivity> activityTestRule =
			new ActivityTestRule<>(RecipeListActivity.class);

	@Test
	public void clickOnRecyclerViewItem_opensRecipeActivity() {
		onView(withId(R.id.recycler_view))
				.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

		onView(withId(R.id.ingredients_label_tv))
				.check(matches(isDisplayed()));
	}
}