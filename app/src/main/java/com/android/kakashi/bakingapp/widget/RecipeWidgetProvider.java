package com.android.kakashi.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.kakashi.bakingapp.Prefs;
import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.controller.RecipeActivity;
import com.android.kakashi.bakingapp.data.model.Recipe;

public class RecipeWidgetProvider extends AppWidgetProvider {

	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
	                            int appWidgetId) {

		Recipe recipe = Prefs.loadRecipe(context);
		if (recipe != null) {
			PendingIntent pendingIntent = PendingIntent.getActivity(
					context,
					0,
					RecipeActivity.startActivity(context, recipe),
					PendingIntent.FLAG_CANCEL_CURRENT
			);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
			views.setTextViewText(R.id.recipe_widget_name_text, recipe.getName());
			views.setOnClickPendingIntent(R.id.recipe_widget_name_text, pendingIntent);

			// populate list view
			Intent intent = new Intent(context, WidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			views.setRemoteAdapter(R.id.recipe_widget_ingredients_list, intent);
			appWidgetManager.updateAppWidget(appWidgetId, views);
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_ingredients_list);

			// Instruct the widget manager to update the widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}
}
