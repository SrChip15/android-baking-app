package com.android.kakashi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.kakashi.bakingapp.data.model.Ingredient;
import com.android.kakashi.bakingapp.data.model.Recipe;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {
    
    private LayoutInflater inflater;
    @SuppressWarnings("unused") private List<Recipe> recipes;
    @LayoutRes private int layoutResId = 0;
    private Context context;
    private int appWidgetId;
    private boolean isHostActivityAppWidgetConfig;
    
    public RecipeListAdapter(Context context, @NonNull List<Recipe> recipes,
                             @LayoutRes int activityCode, int appWidgetId) {
        super(context, 0, recipes);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.appWidgetId = appWidgetId;
    
        if (activityCode == RecipeListActivity.MAIN_SCREEN_ACTIVITY) {
            layoutResId = android.R.layout.simple_list_item_1;
            isHostActivityAppWidgetConfig = false;
        } else if (activityCode ==
                AppWidgetConfigActivity.APP_WIDGET_CONFIGURE_ACTIVITY_CODE) {
            layoutResId = R.layout.list_item_app_widget_configure;
            isHostActivityAppWidgetConfig = true;
        }
    }
    
    @Override
    public int getCount() {
        return recipes == null ? 0 : recipes.size();
    }
    
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutResId, parent, false);
        }
    
        RecipeNameHolder view = new RecipeNameHolder(convertView, isHostActivityAppWidgetConfig);
        if (!isHostActivityAppWidgetConfig) {
            view.recipeNameTextView.setText(recipes.get(position).getName());
    
        } else {
            final Recipe recipe = recipes.get(position);
            view.recipeItemRadioButton.setText(recipe.getName());
            view.recipeItemRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    RemoteViews views = new RemoteViews(
                            context.getPackageName(),
                            R.layout.recipe_widget
                    );
                    
                    List<Ingredient> ingredients = recipe.getIngredients();
                    StringBuilder sb = new StringBuilder();
                    
                    for (int i = 0; i < ingredients.size(); i++) {
                        Ingredient ingredient = ingredients.get(i);
                        String recipeName = ingredient.getIngredient();
                        float quantity = ingredient.getQuantity();
                        sb.append(context.getString(R.string.ingredients_line_item, recipeName, quantity));
                    }
                    
                    views.setTextViewText(R.id.appwidget_text, sb.toString());
                    appWidgetManager.updateAppWidget(appWidgetId, views);
        
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    ((AppCompatActivity) context).setResult(RESULT_OK, resultValue);
                    ((AppCompatActivity) context).finish();
                }
            });
        }
        
        
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
        RadioButton recipeItemRadioButton;
        
        RecipeNameHolder(View cacheView, boolean isHostActivityAppWidgetConfig) {
            if (!isHostActivityAppWidgetConfig) {
                recipeNameTextView = cacheView.findViewById(android.R.id.text1);
            } else {
                recipeItemRadioButton = cacheView.findViewById(R.id.recipe_item_name_radio_button);
            }
        }
    }
}
