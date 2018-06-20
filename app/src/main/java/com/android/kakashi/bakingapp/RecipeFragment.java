package com.android.kakashi.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;

    public static RecipeFragment newInstance() {
        // vanilla for now
        return new RecipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);

        String heading = "Ingredients".toUpperCase();
        ingredientsTextView.setText(heading);

        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        StepAdapter stepAdapter = new StepAdapter(getActivity());
        stepsRecyclerView.setAdapter(stepAdapter);

        return view;
    }
}
