package com.android.kakashi.bakingapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;

import java.util.List;

import timber.log.Timber;

public class RecipeListModel extends ViewModel {

	private MutableLiveData<List<Recipe>> recipes;
	private AsyncTask<Void, Void, List<Recipe>> recipeFetcher;

	public LiveData<List<Recipe>> getRecipes() {
		if (recipes == null) {
			recipes = new MutableLiveData<>();
			recipeFetcher = new FetchRecipesTask().execute();
		}

		return recipes;
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		recipeFetcher.cancel(true);
		recipeFetcher = null;
	}

	/* Asynchronous call to fetch recipes from remote location */
	@SuppressLint("StaticFieldLeak")
	private class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

		@Override
		protected List<Recipe> doInBackground(Void... voids) {
			Timber.d("Fetching Recipes...");
			return NetworkModule.fetchRecipes();
		}

		@Override
		protected void onPostExecute(List<Recipe> recipeList) {
			recipes.setValue(recipeList);
		}
	}
}
