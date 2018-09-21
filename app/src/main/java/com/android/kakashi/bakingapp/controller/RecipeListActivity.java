package com.android.kakashi.bakingapp.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.RecipeListFragment;

public class RecipeListActivity
		extends ModularBaseActivity
		implements RecipeListFragment.Callback {

	@Override
	public Fragment getFragment() {
		return RecipeListFragment.newInstance();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}

	@Override
	public void recipeClicked(Recipe recipe) {
		Intent recipeActivity = RecipeActivity.startActivity(this, recipe);
		startActivity(recipeActivity);
	}
}
