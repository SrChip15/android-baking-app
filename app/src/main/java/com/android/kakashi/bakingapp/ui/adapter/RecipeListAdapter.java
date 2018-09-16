package com.android.kakashi.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.kakashi.bakingapp.data.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

	private LayoutInflater inflater;
	private List<Recipe> recipes;


	public RecipeListAdapter(Context context, @NonNull List<Recipe> recipes) {
		super(context, 0, recipes);
		this.recipes = recipes;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return recipes == null ? 0 : recipes.size();
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		}

		TextView recipeNameTextView = convertView.findViewById(android.R.id.text1);
		recipeNameTextView.setText(recipes.get(position).getName());

		return convertView;
	}

	public void setData(List<Recipe> recipes) {
		this.recipes = recipes;
		notifyDataSetChanged();
	}
}
