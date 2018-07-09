package com.android.kakashi.bakingapp;

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
    @SuppressWarnings("unused") private List<Recipe> recipes;
    
    public RecipeListAdapter(Context context, @NonNull List<Recipe> recipes) {
        super(context, 0, recipes);
        inflater = LayoutInflater.from(context);
        this.recipes = recipes;
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
    
        RecipeNameHolder view = new RecipeNameHolder(convertView);
        view.recipeNameTextView.setText(recipes.get(position).getName());
        
        return convertView;
    }
    
    public void setData(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
    
    /**
     * Caching class for the recipe name view holder. Not really needed since there are only 4 recipes
     * In this case, it may be an overkill. However, it is implemented since it is best practice.
     */
    static class RecipeNameHolder {
        
        TextView recipeNameTextView;
        
        RecipeNameHolder(View cacheView) {
            recipeNameTextView = cacheView.findViewById(android.R.id.text1);
        }
    }
}
