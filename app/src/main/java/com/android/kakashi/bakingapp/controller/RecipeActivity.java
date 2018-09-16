package com.android.kakashi.bakingapp.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.RecipeFragment;

public class RecipeActivity extends ModularBaseActivity {

	private static final String EXTRA_RECIPE = "com.android.kakashi.bakingapp.extra_recipe";

	public static Intent startActivity(Context packageContext, Recipe recipe) {
		Intent intent = new Intent(packageContext, RecipeActivity.class);
		intent.putExtra(EXTRA_RECIPE, recipe);

		return intent;
	}

	@Override
	public Fragment getFragment() {
		Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
		return RecipeFragment.newInstance(recipe);

	}

	@SuppressWarnings("ConstantConditions")
	public void setActionBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	@SuppressWarnings("ConstantConditions")
	public void setHomeAsUpEnabled(boolean showHomeAsUp) {
		getSupportActionBar().setDisplayShowHomeEnabled(showHomeAsUp);
	}
}
