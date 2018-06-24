package com.android.kakashi.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.ui.RecipeFragment;

public class RecipeActivity extends ModularBaseActivity {

    private static final String EXTRA_RECIPE_INDEX = "com.android.kakashi.bakingapp.extra_recipe_index";

    public static Intent startActivity(Context packageContext, int recipePosition) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE_INDEX, recipePosition);

        return intent;
    }

    @Override
    public Fragment getFragment() {
        return RecipeFragment.newInstance(getIntent().getIntExtra(EXTRA_RECIPE_INDEX, -1));
    }

    @SuppressWarnings("ConstantConditions")
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
