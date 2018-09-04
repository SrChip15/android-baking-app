package com.android.kakashi.bakingapp.controller;

import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.ui.RecipeListFragment;

public class RecipeListActivity extends ModularBaseActivity {
    
    public static final int MAIN_SCREEN_ACTIVITY = 1111;
    
    @Override
    public Fragment getFragment() {
        return RecipeListFragment.newInstance(MAIN_SCREEN_ACTIVITY, 0);
    }
}
