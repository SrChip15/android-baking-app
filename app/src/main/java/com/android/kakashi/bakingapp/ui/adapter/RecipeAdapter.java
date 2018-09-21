package com.android.kakashi.bakingapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

	private Context context;
	private List<Recipe> recipes;

	public RecipeAdapter(Context context, @NonNull List<Recipe> recipes) {
		super();
		this.context = context;
		this.recipes = recipes;
	}

	@NonNull
	@Override
	public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(context).inflate(
				R.layout.list_item_recipe,
				parent,
				false
		);
		return new RecipeHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
		Recipe recipe = recipes.get(position);
		holder.bind(recipe.getName(), recipe.getServings());
	}

	@Override
	public int getItemCount() {
		return recipes.size();
	}

	public void setData(List<Recipe> recipes) {
		this.recipes.clear();
		this.recipes = recipes;
		notifyDataSetChanged();
	}

	class RecipeHolder extends RecyclerView.ViewHolder {

		TextView recipeNameTextView;
		TextView recipeServingSize;

		public RecipeHolder(View itemView) {
			super(itemView);
			recipeNameTextView = itemView.findViewById(R.id.recipe_title);
			recipeServingSize = itemView.findViewById(R.id.recipe_servings);
		}

		void bind(String name, int servingSize) {
			recipeNameTextView.setText(name);
			recipeServingSize.setTextColor(Color.BLACK);
			recipeServingSize.setText(String.valueOf(servingSize));
		}
	}
}
