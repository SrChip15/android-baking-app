package com.android.kakashi.bakingapp.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.RecipeFragment;
import com.android.kakashi.bakingapp.ui.RecipeListFragment;
import com.android.kakashi.bakingapp.ui.RecipeListFragment.RecipeClickListener;

public class RecipeListActivity
		extends ModularBaseActivity
		implements RecipeClickListener {

	@Override
	public Fragment getFragment() {
		return RecipeListFragment.newInstance();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}

	@Override
	public void onRecipeItemClicked(Recipe recipe) {
		if (findViewById(R.id.detail_fragment_container) != null) {
			// Tablet mode detected
			Fragment fragment = RecipeFragment.newInstance(recipe);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.detail_fragment_container, fragment)
					.commit();
		} else {
			// Phone mode detected
			Intent fireRecipeActivity = RecipeActivity.startActivity(this, recipe);
			startActivity(fireRecipeActivity);
		}
	}
}
