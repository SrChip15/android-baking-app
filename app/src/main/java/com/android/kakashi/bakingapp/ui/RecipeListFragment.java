package com.android.kakashi.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.android.kakashi.bakingapp.RecipeListModel;
import com.android.kakashi.bakingapp.controller.RecipeActivity;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.ui.adapter.RecipeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

	@BindView(R.id.list_view)
	ListView listView;

	public static RecipeListFragment newInstance() {
		Bundle args = new Bundle();
		RecipeListFragment fragment = new RecipeListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View ui = inflater.inflate(R.layout.fragment_recipe_list, container, false);
		ButterKnife.bind(this, ui);


		final RecipeListAdapter adapter =
				new RecipeListAdapter(getActivity(), new ArrayList<Recipe>());
		final RecipeListModel model =
				ViewModelProviders.of(requireActivity()).get(RecipeListModel.class);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				List<Recipe> recipes = model.getRecipes().getValue();
				Recipe recipe = null;
				if (recipes != null && !recipes.isEmpty()) {
					recipe = recipes.get(position);
				}

				Intent fireRecipeActivity = RecipeActivity.startActivity(requireActivity(), recipe);
				startActivity(fireRecipeActivity);
			}
		});

		final Observer<List<Recipe>> listObserver = new Observer<List<Recipe>>() {
			@Override
			public void onChanged(@Nullable List<Recipe> recipeList) {
				if (recipeList != null) {
					adapter.addAll(recipeList);
					adapter.notifyDataSetChanged();
				}
			}
		};

		model.getRecipes().observe(requireActivity(), listObserver);

		return ui;
	}
}
