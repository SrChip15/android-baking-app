package com.android.kakashi.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.kakashi.bakingapp.data.model.Recipe;

public final class Prefs {

	public static final String PREFS_NAME = "prefs";

	private Prefs() {
		// Restrict class object instantiation
	}

	public static void saveRecipe(Context context, Recipe recipe) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
		prefs.putString(context.getString(R.string.widget_recipe_name_key), recipe.flatten());

		prefs.apply();
	}

	public static Recipe loadRecipe(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String recipeAsString = prefs.getString(context.getString(R.string.widget_recipe_name_key), "");

		return Recipe.remake(recipeAsString);
	}
}
