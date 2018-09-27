package com.android.kakashi.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.controller.RecipeActivity;
import com.android.kakashi.bakingapp.data.model.Ingredient;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.adapter.StepAdapter;
import com.android.kakashi.bakingapp.ui.adapter.StepAdapter.OnItemClickListener;
import com.android.kakashi.bakingapp.widget.WidgetService;

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
	private Recipe recipe;
	private Callback callback;

	private static final String ARG_RECIPE = "recipe";

	public interface Callback {
		void onStepSelected(int stepPosition, Recipe recipe);
	}

	public static RecipeFragment newInstance(Recipe recipe) {
		Bundle args = new Bundle();
		args.putParcelable(ARG_RECIPE, recipe);

		RecipeFragment fragment = new RecipeFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof RecipeActivity) {
			((RecipeActivity) requireActivity()).setHomeAsUpEnabled(true);
		}

		// Dependency - Hosting activity must implement callback interface
		callback = (Callback) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); // handle activity up navigation

		if (getArguments() != null) {
			recipe = getArguments().getParcelable(ARG_RECIPE);
		} else {
			throw new IllegalArgumentException("ERROR! Recipe missing!");
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.recipe, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int i = item.getItemId();
		if (i == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(requireActivity());
			return true;
		} else if (i == R.id.action_add_widget) {
			WidgetService.updateWidget(requireActivity(), recipe);
			Snackbar.make(getView(), "Recipe added to widget", Snackbar.LENGTH_SHORT).show();
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

		if (requireActivity() instanceof RecipeActivity) {
			((RecipeActivity) requireActivity()).setActionBarTitle(recipe.getName());
		}

		List<Ingredient> ingredients = recipe.getIngredients();
		for (int i = 0; i < ingredients.size(); i++) {
			Ingredient ingredient = ingredients.get(i);
			String name = ingredient.getName();
			float quantity = ingredient.getQuantity();
			String ingredientText = getString(R.string.ingredients_line_item, (i + 1), name, quantity, ingredient.getMeasure());
			ingredientsTextView.append(ingredientText);
		}

		stepsRecyclerView.setHasFixedSize(true);
		stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false));

		StepAdapter stepAdapter = new StepAdapter(getActivity(), this, recipe.getSteps());
		stepsRecyclerView.setAdapter(stepAdapter);


		return view;
	}

	/**
	 * The below interface method from StepAdapter is devised to inform the adapter powering the
	 * RecyclerView in the fragment_recipe.xml displaying all the recipe steps.
	 *
	 * @param position an integer denoting the position of the step being clicked in the list of steps
	 */
	@Override
	public void onItemClicked(int position) {
		callback.onStepSelected(position, recipe);
	}
}
