package com.udacity.bakingapp.models;

import android.content.Context;

import org.parceler.Parcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Java Object representing a single recipe with utility methods to read from assets
 */
@Parcel
public class Recipe {

    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

    public Recipe() {
    }

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public static String readRecipesFromJSON(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


//    public static List<Recipe> parseJSON(Context context, String json) {
//        List<Recipe> recipes = null;
//        try {
//            JSONArray recipesArray = new JSONArray(readRecipesFromJSON(context));
//
//            for (int i = 0; i < recipesArray.length(); i++) {
//                JSONObject recipeObject = recipesArray.getJSONObject(i);
//
//                int id = recipeObject.getInt("id");
//                String name = recipeObject.getString("name");
//                recipeObject.get
//
//
//                Recipe recipe = new Recipe();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return recipes;
//    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<Ingredient> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(List<Ingredient> ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public List<Step> getSteps() {
//        return steps;
//    }
//
//    public void setSteps(List<Step> steps) {
//        this.steps = steps;
//    }
//
//    public int getServings() {
//        return servings;
//    }
//
//    public void setServings(int servings) {
//        this.servings = servings;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

}
