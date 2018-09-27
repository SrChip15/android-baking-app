package com.android.kakashi.bakingapp.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityIntentTest {

	private static final String EXTRA_RECIPE = "recipeId";

	@Rule
	public IntentsTestRule<RecipeListActivity> intentsTestRule
			= new IntentsTestRule<>(RecipeListActivity.class);

	private Recipe recipe;

	@Before
	public void setUp() {
		recipe = mock(Recipe.class);
	}

	@Test
	public void clickOnRecyclerViewItem_runsRecipeActivityIntent() {
		Intent resultData = new Intent();
		resultData.putExtra(EXTRA_RECIPE, recipe);
		ActivityResult result = new ActivityResult(Activity.RESULT_OK, resultData);

		// Click first item in recipe list
		onView(ViewMatchers.withId(R.id.recycler_view))
				.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		// Set up result stubbing when an intent sent to "RecipeActivity" is seen.
		intending(toPackage("com.android.kakashi.bakingapp")).respondWith(result);

		// Check whether the ingredients label is present on screen. This confirms that the intent
		// originating from the recipe click works!
		onView(withId(R.id.ingredients_label_tv)).check(matches(isDisplayed()));
	}
}