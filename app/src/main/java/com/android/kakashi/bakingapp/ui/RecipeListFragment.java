package com.android.kakashi.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.RecipeListModel;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.adapter.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment
		extends Fragment
		implements RecipeAdapter.RecipeClickListener {

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;
	private Callback callback;

	public interface Callback {
		void recipeClicked(Recipe recipe);
	}

	public static RecipeListFragment newInstance() {
		Bundle args = new Bundle();
		RecipeListFragment fragment = new RecipeListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		// Dependency - Hosting activity must implement callback interface
		callback = (Callback) context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View ui = inflater.inflate(R.layout.fragment_recipe_list, container, false);
		ButterKnife.bind(this, ui);


		final RecipeAdapter adapter =
				new RecipeAdapter(requireActivity(), this, new ArrayList<Recipe>());
		final RecipeListModel model =
				ViewModelProviders.of(requireActivity()).get(RecipeListModel.class);

		if (getResources().getBoolean(R.bool.isTablet)) {
			int spanCount = requireContext()
					                .getResources()
					                .getConfiguration()
					                .orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
			recyclerView.setLayoutManager(new GridLayoutManager(
					requireActivity(),
					spanCount
			));
		} else {
			recyclerView.setLayoutManager(new LinearLayoutManager(
					requireActivity(),
					LinearLayoutManager.VERTICAL,
					false
			));
		}
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);

		final Observer<List<Recipe>> listObserver = new Observer<List<Recipe>>() {
			@Override
			public void onChanged(@Nullable List<Recipe> recipeList) {
				if (recipeList != null) {
					adapter.setData(recipeList);
				}
			}
		};

		model.getRecipes().observe(requireActivity(), listObserver);

		return ui;
	}

	@Override
	public void onRecipeClicked(Recipe recipe) {
		callback.recipeClicked(recipe);
	}
}
