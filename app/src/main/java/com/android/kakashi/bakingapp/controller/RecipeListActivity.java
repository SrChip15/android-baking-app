package com.android.kakashi.bakingapp.controller;

import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.ui.RecipeListFragment;

public class RecipeListActivity extends ModularBaseActivity {

	@Override
	public Fragment getFragment() {
		return RecipeListFragment.newInstance();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}
}
