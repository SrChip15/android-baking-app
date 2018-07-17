package com.android.kakashi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.kakashi.bakingapp.ui.RecipeListFragment;

public class AppWidgetConfigActivity extends ModularBaseActivity {
    
    private int appWidgetId;
    public static final int APP_WIDGET_CONFIGURE_ACTIVITY_CODE = 2222;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
            );
        }
    }
    
    @Override
    public Fragment getFragment() {
        return RecipeListFragment.newInstance(APP_WIDGET_CONFIGURE_ACTIVITY_CODE, appWidgetId);
    }
}
