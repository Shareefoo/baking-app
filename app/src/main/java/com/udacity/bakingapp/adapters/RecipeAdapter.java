package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.activities.RecipeActivity;
import com.udacity.bakingapp.models.Recipe;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Basic adapter class to inflate the recipes
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView_recipe)
        ImageView recipeImageView;

        @BindView(R.id.textView_name)
        TextView nameTextView;

        @BindView(R.id.textView_servings)
        TextView servingsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        // Handles the row being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            Recipe recipe = mRecipes.get(position);

            Intent recipeDetailsIntent = new Intent(mContext, RecipeActivity.class);
            recipeDetailsIntent.putExtra("recipe", Parcels.wrap(recipe));
            mContext.startActivity(recipeDetailsIntent);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_recipe, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(recipe.name);

        TextView servingsTextView = viewHolder.servingsTextView;
        servingsTextView.setText(String.valueOf(recipe.servings));

        ImageView recipeImageView = viewHolder.recipeImageView;
        if (!TextUtils.isEmpty(recipe.image)) {
            Picasso.get()
                    .load(recipe.image)
                    .into(recipeImageView);
        } else {
            recipeImageView.setImageResource(R.drawable.cake);
        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

}