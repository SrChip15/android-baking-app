package com.android.kakashi.bakingapp;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

public class RecipeListModel extends ViewModel {
    private List<Recipe> recipes;
    
    public List<Recipe> getRecipes() {
        if (recipes == null) {
            recipes = new ArrayList<>();
            loadRecipes(recipes);
        }
        
        return recipes;
    }
    
    private void loadRecipes(List<Recipe> recipes) {
        new FetchRecipesTask(recipes).execute();
    }
    
    /*
    Asynchronous call to fetch recipes from remote location
     */
    static class FetchRecipesTask extends AsyncTask<Void, Void, Void> {
        
        @SuppressWarnings("unused")
        private List<Recipe> recipes;
        
        FetchRecipesTask(List<Recipe> recipes) {
            this.recipes= recipes;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            recipes = NetworkModule.fetchRecipes();
            return null;
        }
    }
}
