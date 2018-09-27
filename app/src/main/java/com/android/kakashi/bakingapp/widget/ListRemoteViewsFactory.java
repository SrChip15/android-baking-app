package com.android.kakashi.bakingapp.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.kakashi.bakingapp.Prefs;
import com.android.kakashi.bakingapp.data.model.Recipe;

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private Context context;
	private Recipe recipe;

	public ListRemoteViewsFactory(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDataSetChanged() {
		recipe = Prefs.loadRecipe(context);
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public int getCount() {
		return recipe.getIngredients().size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews row = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
		row.setTextColor(android.R.id.text1, Color.WHITE);
		row.setTextViewText(android.R.id.text1, recipe.getIngredients().get(position).getName());

		return row;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}
