package com.android.kakashi.bakingapp.ui;

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
import android.widget.ListView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.RecipeActivity;
import com.android.kakashi.bakingapp.RecipeListAdapter;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.list_view) ListView listView;
    @SuppressWarnings("FieldCanBeLocal") private NetworkModule networkModule;
    private RecipeListAdapter adapter;

    public static RecipeListFragment newInstance() {
        // vanilla for now
        return new RecipeListFragment();
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        networkModule = NetworkModule.getInstance();
    
        List<Recipe> recipes = new ArrayList<>();
        if (networkModule.getRecipes() == null) {
            adapter = new RecipeListAdapter(context, recipes);
        } else {
            adapter.setData(networkModule.getRecipes());
        }
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    
        new FetchRecipesTask(networkModule, adapter).execute();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent fireRecipeActivity = RecipeActivity.startActivity(getActivity(), position);
                startActivity(fireRecipeActivity);
            }
        });

        return view;
    }
    
    static class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

        private NetworkModule networkModule;
        private RecipeListAdapter adapter;

        FetchRecipesTask(NetworkModule networkModule, RecipeListAdapter adapter) {
            this.networkModule = networkModule;
            this.adapter = adapter;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            List<Recipe> recipes = NetworkModule.fetchRecipes();
            networkModule.cacheData(recipes);

            return recipes;
        }
    
        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            adapter.setData(recipes);
        }
    }
}
