package com.android.kakashi.bakingapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.RecipeActivity;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.list_view)
    ListView listView;
    @SuppressWarnings("FieldCanBeLocal")
    private NetworkModule networkModule;

    public static RecipeListFragment newInstance() {
        // vanilla for now
        return new RecipeListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkModule = NetworkModule.getInstance();
        new FetchRecipesTask(networkModule).execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent fireRecipeActivity = RecipeActivity.startActivity(getActivity(), position);
                startActivity(fireRecipeActivity);
            }
        });

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchRecipesTask extends AsyncTask<Void, Void, Void> {

        private NetworkModule networkModule;

        FetchRecipesTask(NetworkModule networkModule) {
            this.networkModule = networkModule;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Recipe> recipes = NetworkModule.fetchRecipes();
            networkModule.cacheData(recipes);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            List<String> recipeNames = new ArrayList<>();
            List<Recipe> recipes = networkModule.getRecipes();
            for (int i = 0; i < recipes.size(); i++) {
                recipeNames.add(recipes.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    Objects.requireNonNull(getActivity()),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    recipeNames
            );

            listView.setAdapter(adapter);
        }
    }
}
