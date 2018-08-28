package com.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.api.ApiClient;
import com.udacity.bakingapp.api.ApiInterface;
import com.udacity.bakingapp.data.SpManager;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Recipe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoveViewsFactory(this.getApplicationContext(), intent);
    }

}

class IngredientsRemoveViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = IngredientsRemoveViewsFactory.class.getSimpleName();

    private static final int mCount = 10;

    private List<Ingredient> mIngredients;
    private Context mContext;
    private int mAppWidgetId;

    public IngredientsRemoveViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // Initialize the data set or any connections / cursors to the data source.
    public void onCreate() {
        Log.d("DDD", "onCreate: ");
        mIngredients = new ArrayList<Ingredient>();

//        populateListItem();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void populateListItem() {
        for (int i = 0; i < mCount; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.quantity = i;
            ingredient.measure = "M  " + i;
            ingredient.ingredient = "I " + i;
            mIngredients.add(ingredient);
        }
    }

    public RemoteViews getViewAt(int position) {
        Log.d("DDD", "getViewAt: ");
        // Construct a RemoteViews item based on the app widget item XML file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_ingredient);

        Ingredient ingredient = mIngredients.get(position);

        rv.setTextViewText(R.id.textView_quantity, String.valueOf(ingredient.quantity));
        rv.setTextViewText(R.id.textView_measure, String.valueOf(ingredient.measure));
        rv.setTextViewText(R.id.textView_ingredient, String.valueOf(ingredient.ingredient));

//        // Next, set a fill-intent, which will be used to fill in the pending intent template
//        // that is set on the collection view in IngredientsWidgetProvider.
//        Bundle extras = new Bundle();
//        extras.putInt(IngredientsWidgetProvider.EXTRA_ITEM, position);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//
//        rv.setOnClickFillInIntent(R.id.container, fillInIntent);

//        // You can do heaving lifting in here, synchronously. For example, if you need to
//        // process an image, fetch something from the network, etc., it is ok to do it here,
//        // synchronously. A loading view will show up in lieu of the actual contents in the
//        // interim.
//        try {
//            System.out.println("Loading view " + position);
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // Return the RemoteViews object.
        return rv;
    }


    @Override
    public int getCount() {
        return mIngredients.size();
//        return mCount;
    }

    @Override
    public void onDestroy() {
        // tear down anything that was setup for the data source eg. cursors, connections, etc.
        mIngredients.clear();
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

    @Override
    public void onDataSetChanged() {
        Log.d("DDD", "onDataSetChanged: ");
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory.
        // Fetching JSON data from server and add them to records arraylist

        loadLastVisitedRecipeIngredients();
    }

    private void loadLastVisitedRecipeIngredients() {
        //
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Recipe>> recipesResponseCall = apiService.getRecipes();
        recipesResponseCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                Log.d(TAG, "onResponse: " + response.toString());

                SpManager spManager = SpManager.getInstance(mContext);
                int recipeId = spManager.getInt("last_visited_recipe", 0);
                Log.d(TAG, "onResponse: Recipe ID: " + recipeId);

                if (response.isSuccessful()) {

                    List<Recipe> recipes = response.body();

                    if (recipes != null) {

                        for (Recipe recipe : recipes) {
                            if (recipe.id == recipeId) {
                                mIngredients = recipe.ingredients;
                            }
                        }

                    }

                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        Log.e(TAG, "onResponse: " + errorObject.toString());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}


