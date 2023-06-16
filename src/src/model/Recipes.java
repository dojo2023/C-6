package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipes implements Serializable{
	private List<Recipe>recipes = new ArrayList<Recipe>();

	public Recipes(Recipe recipe) {
		this.recipes.add(recipe);
	}

	public Recipes() {
		this.recipes = null;
	}

	// get, setメソッドの追加
	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipe) {
		for(int i=0; i<recipe.size(); i++) {
			this.recipes.add(recipe.get(i));
		}
	}

}
