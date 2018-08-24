package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Basic adapter class to inflate the recipes
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_quantity)
        TextView quantityTextView;

        @BindView(R.id.textView_measure)
        TextView measureTextView;

        @BindView(R.id.textView_ingredient)
        TextView ingredientTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_ingredient, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Ingredient ingredient = mIngredients.get(position);

        // Set item views based on your views and data model
        TextView quantityTextView = viewHolder.quantityTextView;
        quantityTextView.setText(String.valueOf(ingredient.quantity));

        TextView measureTextView = viewHolder.measureTextView;
        measureTextView.setText(String.valueOf(ingredient.measure));

        TextView ingredientTextView = viewHolder.ingredientTextView;
        ingredientTextView.setText(String.valueOf(ingredient.ingredient));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

}