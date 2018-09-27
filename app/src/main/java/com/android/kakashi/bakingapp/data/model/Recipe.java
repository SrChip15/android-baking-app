/* Created using jsonschema2pojo.org */

package com.android.kakashi.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("unused")
public class Recipe implements Parcelable {

	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("ingredients")
	@Expose
	private List<Ingredient> ingredients = null;
	@SerializedName("steps")
	@Expose
	private List<Step> steps = null;
	@SerializedName("servings")
	@Expose
	private int servings;
	@SerializedName("image")
	@Expose
	private String image;

	protected Recipe(Parcel in) {
		id = in.readInt();
		name = in.readString();
		ingredients = in.createTypedArrayList(Ingredient.CREATOR);
		steps = in.createTypedArrayList(Step.CREATOR);
		servings = in.readInt();
		image = in.readString();
	}

	public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel in) {
			return new Recipe(in);
		}

		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeTypedList(ingredients);
		dest.writeTypedList(steps);
		dest.writeInt(servings);
		dest.writeString(image);
	}

	/**
	 * Helper method to flatten a Recipe object into a String
	 * @return String flattened list of ingredients of recipe
	 */
	public String flatten() {
		return new Gson().toJson(this);
	}

	/**
	 * Helper method to remake a Recipe object from a flattened one that exists as a String
	 * @return Recipe a recipe object
	 */
	public static Recipe remake(String recipeAsString) {
		Type type = new TypeToken<Recipe>(){}.getType();
		return new Gson().fromJson(recipeAsString, type);
	}
}
