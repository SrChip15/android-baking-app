package com.android.kakashi.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepPagerActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager stepPager;

    private static final String EXTRA_STEP_INDEX = "com.android.kakashi.bakingapp.step_index";

    public static Intent start(Context packageContext, int currentIndex) {
        Intent intent = new Intent(packageContext, StepPagerActivity.class);
        intent.putExtra(EXTRA_STEP_INDEX, currentIndex);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_pager);
        ButterKnife.bind(this);

        int currentIndex = getIntent().getIntExtra(EXTRA_STEP_INDEX, 0);

        final List<String> steps = MockData.getData();
        FragmentManager fragmentManager = getSupportFragmentManager();

        stepPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                String step = steps.get(position);
                return StepFragment.newInstance(step);
            }

            @Override
            public int getCount() {
                return steps.size();
            }
        });

        stepPager.setCurrentItem(currentIndex);
    }
}
