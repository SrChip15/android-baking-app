package com.android.kakashi.bakingapp.ui.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.model.Step;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

	private Context context;
	private List<Recipe> recipes;
	private RecipeClickListener recipeClickListener;

	public interface RecipeClickListener {
		void onRecipeClicked(Recipe recipe);
	}

	public RecipeAdapter(Context context, RecipeClickListener recipeClickListener, @NonNull List<Recipe> recipes) {
		super();
		this.context = context;
		this.recipes = recipes;
		this.recipeClickListener = recipeClickListener;
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
		List<Step> steps = recipe.getSteps();
		String recipeThumbnail = steps.get(steps.size() - 1).getVideoURL();
		holder.bind(recipe.getName(), recipe.getServings(), recipeThumbnail);
	}

	@Override
	public int getItemCount() {
		return recipes.size();
	}

	public void setData(List<Recipe> recipes) {
		this.recipes.clear();
		this.recipes.addAll(recipes);
		notifyDataSetChanged();
	}

	class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView recipeNameTextView;
		TextView recipeServingSize;
		ImageView recipeImage;
		TextView recipeServingsInfo;
		boolean isInLandscapeMode;
		boolean isTablet;

		public RecipeHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);

			recipeNameTextView = itemView.findViewById(R.id.recipe_title);
			recipeServingSize = itemView.findViewById(R.id.recipe_servings);
			recipeImage = itemView.findViewById(R.id.recipe_image);

			isInLandscapeMode = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
			isTablet = context.getResources().getBoolean(R.bool.isTablet);
			if (isInLandscapeMode && !isTablet) {
				recipeServingsInfo = itemView.findViewById(R.id.recipe_servings_info);
			}
		}

		void bind(String name, int servingSize, String videoUrl) {
			recipeNameTextView.setText(name);
			String servingsSizeAsText = String.valueOf(servingSize);
			recipeServingSize.setText(servingsSizeAsText);
			Glide.with(context)
					.load(videoUrl)
					.into(recipeImage);

			if (isInLandscapeMode && !isTablet) {
				recipeServingsInfo.setText(String.format("Serves %s", servingsSizeAsText));
			}
		}

		@Override
		public void onClick(View v) {
			recipeClickListener.onRecipeClicked(recipes.get(getAdapterPosition()));
		}
	}
}
