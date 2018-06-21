package com.android.kakashi.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.ui.RecipeFragment;

import timber.log.Timber;

public class RecipeActivity extends ModularBaseActivity {
    public static Intent startActivity(Context packageContext, String itemClicked) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        Timber.i("Passed-in value = %s", itemClicked);

        return intent;
    }

    @Override
    public Fragment getFragment() {
        return RecipeFragment.newInstance();
    }
}
