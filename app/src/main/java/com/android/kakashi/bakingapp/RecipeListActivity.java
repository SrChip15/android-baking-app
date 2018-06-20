package com.android.kakashi.bakingapp;

import android.support.v4.app.Fragment;

public class RecipeListActivity extends ModularBaseActivity {

    @Override
    public Fragment getFragment() {
        return RecipeListFragment.newInstance();
    }
}
