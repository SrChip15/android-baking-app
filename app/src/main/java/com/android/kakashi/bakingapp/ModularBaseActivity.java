package com.android.kakashi.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class ModularBaseActivity extends AppCompatActivity {
    public abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment ui = fm.findFragmentById(R.id.fragment_container);

        if (ui == null) {
            ui = getFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, ui)
                    .commit();
        }
    }
}
