package com.android.kakashi.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.controller.RecipeActivity;
import com.android.kakashi.bakingapp.data.model.Ingredient;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;
import com.android.kakashi.bakingapp.ui.adapter.StepAdapter;
import com.android.kakashi.bakingapp.ui.adapter.StepAdapter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment
        extends Fragment
        implements OnItemClickListener {

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;
    private int recipeIndex;
    private List<Recipe> recipes;

    private static final String ARG_RECIPE_INDEX = "recipeIndex";
    private static final int INVALID_INDEX = -1;

    public static RecipeFragment newInstance(int recipeIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_INDEX, recipeIndex);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // handle activity up navigation

        recipes = NetworkModule.getInstance().getRecipes();

        if (getArguments() != null) {
            recipeIndex = getArguments().getInt(ARG_RECIPE_INDEX);
        } else {
            throw new IllegalArgumentException("ERROR! Recipe position missing!");
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((RecipeActivity)getActivity()).setHomeAsUpEnabled(true);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);

        if (recipeIndex != INVALID_INDEX) {
            Recipe recipe = recipes.get(recipeIndex);
            //noinspection ConstantConditions
            ((RecipeActivity)getActivity()).setActionBarTitle(recipe.getName());

            List<Ingredient> ingredients = recipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                String name = ingredient.getIngredient();
                float quantity = ingredient.getQuantity();
                ingredientsTextView.append(getString(R.string.ingredients_line_item, name, quantity));
            }

            stepsRecyclerView.setHasFixedSize(true);
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));

            StepAdapter stepAdapter = new StepAdapter(getActivity(), this, recipe.getSteps());
            stepsRecyclerView.setAdapter(stepAdapter);
        }

        return view;
    }

    @Override
    public void onItemClicked(int position) {
        Intent stepPager = StepPagerActivity.start(getActivity(), position, recipeIndex);
        startActivity(stepPager);
    }
}
