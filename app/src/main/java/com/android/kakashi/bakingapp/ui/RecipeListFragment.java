package com.android.kakashi.bakingapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
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
import com.android.kakashi.bakingapp.RecipeListModel;
import com.android.kakashi.bakingapp.controller.RecipeActivity;
import com.android.kakashi.bakingapp.ui.adapter.RecipeListAdapter;
import com.android.kakashi.bakingapp.ui.adapter.RecipeListAdapter.RecipeSelector;
import com.android.kakashi.bakingapp.data.model.Recipe;
import com.android.kakashi.bakingapp.data.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeSelector {
    
    @BindView(R.id.list_view)
    ListView listView;
    @SuppressWarnings("FieldCanBeLocal")
    private NetworkModule networkModule;
    private RecipeListAdapter adapter;
    
    private static final String KEY_HOST_ACTIVITY_CODE = "ActivityCode";
    private static final String KEY_APP_WIDGET_ID = "AppWidgetId";
    
    public static RecipeListFragment newInstance(int activityCode, int appWidgetId) {
        Bundle args = new Bundle();
        args.putInt(KEY_HOST_ACTIVITY_CODE, activityCode);
        args.putInt(KEY_APP_WIDGET_ID, appWidgetId);
        
        RecipeListFragment fragment = new RecipeListFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*networkModule = NetworkModule.getInstance();
        final int hostActivityCode = getArguments().getInt(KEY_HOST_ACTIVITY_CODE);
        final int appWidgetId = getArguments().getInt(KEY_APP_WIDGET_ID);*/
        /*if (networkModule.getRecipes() == null) {
            adapter = new RecipeListAdapter(context, new ArrayList<Recipe>(), hostActivityCode);
        } else {
            adapter.setData(networkModule.getRecipes());
        }*/
        /*adapter = new RecipeListAdapter(context, new ArrayList<Recipe>(), hostActivityCode, appWidgetId);
        adapter.setData(networkModule.getRecipes());*/
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setRetainInstance(true);
        new FetchRecipesTask(networkModule, adapter).execute();*/
        networkModule = NetworkModule.getInstance();
        final int hostActivityCode = getArguments().getInt(KEY_HOST_ACTIVITY_CODE);
        final int appWidgetId = getArguments().getInt(KEY_APP_WIDGET_ID);
        adapter = new RecipeListAdapter(getActivity(), new ArrayList<Recipe>(), hostActivityCode, appWidgetId);
        RecipeListModel model =
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(getActivity().getApplication())
                        .create(RecipeListModel.class);
        adapter.setData(model.getRecipes());
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
    
    @Override
    public void onRecipeSelected() {
        // get ingredients from current recipe, set on view and install widget on home screen
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
