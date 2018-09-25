package com.android.kakashi.bakingapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.model.Step;
import com.android.kakashi.bakingapp.ui.RecipeFragment;
import com.android.kakashi.bakingapp.ui.StepFragment;
import com.android.kakashi.bakingapp.ui.StepPagerActivity;

public class RecipeActivity extends ModularBaseActivity implements RecipeFragment.Callback {

	private static final String EXTRA_RECIPE = "com.android.kakashi.bakingapp.extra_recipe";

	public static Intent startActivity(Context packageContext, Recipe recipe) {
		Intent intent = new Intent(packageContext, RecipeActivity.class);
		intent.putExtra(EXTRA_RECIPE, recipe);

		return intent;
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}

	@Override
	public Fragment getFragment() {
		Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
		return RecipeFragment.newInstance(recipe);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (findViewById(R.id.detail_fragment_container) != null) {
			// Tablet mode; Show recipe introduction in detail screen portion
			FragmentManager fm = getSupportFragmentManager();
			Fragment detailFragment = fm.findFragmentById(R.id.detail_fragment_container);

			if (detailFragment == null) {
				Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
				Fragment fragment = StepFragment.newInstance(recipe.getSteps().get(0));

				fm.beginTransaction()
						.add(R.id.detail_fragment_container, fragment)
						.commit();
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	public void setActionBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	@SuppressWarnings("ConstantConditions")
	public void setHomeAsUpEnabled(boolean showHomeAsUp) {
		getSupportActionBar().setDisplayShowHomeEnabled(showHomeAsUp);
	}

	@Override
	public void onStepSelected(int stepPosition, Recipe recipe) {
		if (findViewById(R.id.detail_fragment_container) != null) {
			// Tablet mode detected
			Step step = recipe.getSteps().get(stepPosition);
			Fragment fragment = StepFragment.newInstance(step);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.detail_fragment_container, fragment)
					.commit();
		} else {
			// Phone mode
			Intent stepPager = StepPagerActivity.start(this, stepPosition, recipe);
			startActivity(stepPager);
		}
	}
}
