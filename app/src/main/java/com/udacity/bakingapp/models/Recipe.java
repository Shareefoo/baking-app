package com.udacity.bakingapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Java Object representing a single recipe with utility methods to read from assets
 */
@Parcel
public class Recipe {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("ingredients")
    public List<Ingredient> ingredients = null;

    @SerializedName("steps")
    public List<Step> steps = null;

    @SerializedName("servings")
    public int servings;

    @SerializedName("image")
    public String image;

//    public static String readRecipesFromJSON(Context context) {
//        String json = null;
//        try {
//            InputStream is = context.getAssets().open("baking.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//
//    }

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

}