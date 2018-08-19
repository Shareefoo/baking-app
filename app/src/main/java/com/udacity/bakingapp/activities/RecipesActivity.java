package com.udacity.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapters.RecipeAdapter;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_recipes)
    RecyclerView mRecyclerViewRecipes;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        //
        String json = Recipe.readRecipesFromJSON(this);

        List<Recipe> recipes = new ArrayList<>();

        try {

            JSONArray recipesArray = new JSONArray(json);

            for (int i = 0; i < recipesArray.length(); i++) {

                JSONObject recipeObject = recipesArray.getJSONObject(i);
                int recipeId = recipeObject.getInt("id");
                String recipeName = recipeObject.getString("name");

                List<Ingredient> recipeIngredients = new ArrayList<>();

                JSONArray ingredientsArray = recipeObject.getJSONArray("ingredients");
                //
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                    double quantity = ingredientObject.getDouble("quantity");
                    String measure = ingredientObject.getString("measure");
                    String ingredientName = ingredientObject.getString("ingredient");
                    Ingredient ingredient = new Ingredient(quantity, measure, ingredientName);
                    recipeIngredients.add(ingredient);
                }

                List<Step> recipeSteps = new ArrayList<>();

                JSONArray stepsArray = recipeObject.getJSONArray("steps");
                //
                for (int j = 0; j < stepsArray.length(); j++) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int id = stepObject.getInt("id");
                    String shortDescription = stepObject.getString("shortDescription");
                    String description = stepObject.getString("description");
                    String videoURL = stepObject.getString("videoURL");
                    String thumbnailURL = stepObject.getString("thumbnailURL");
                    Step step = new Step(id, shortDescription, description, videoURL, thumbnailURL);
                    recipeSteps.add(step);
                }

                int recipeServings = recipeObject.getInt("servings");
                String recipeImage = recipeObject.getString("image");

                Recipe recipe = new Recipe(recipeId, recipeName, recipeIngredients, recipeSteps, recipeServings, recipeImage);

                recipes.add(recipe);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerViewRecipes.setHasFixedSize(true);

        // check if tablet or phone
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
        }

        mRecyclerViewRecipes.setLayoutManager(mLayoutManager);

        mAdapter = new RecipeAdapter(this, recipes);
        mRecyclerViewRecipes.setAdapter(mAdapter);

    }
}
