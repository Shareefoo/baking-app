package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.activities.StepDetailsActivity;
import com.udacity.bakingapp.models.Step;

import org.parceler.Parcels;

import java.util.List;

/**
 * Basic adapter class to inflate the recipes
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mSteps;

    private OnItemClickListener mListener;

    public StepAdapter(Context context, List<Step> steps, OnItemClickListener listener) {
        mContext = context;
        mSteps = steps;
        mListener = listener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            descriptionTextView = itemView.findViewById(R.id.textView_description);

            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            mListener.onItemSelected(position);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_step, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Step step = mSteps.get(position);

        // Set item views based on your views and data model
        TextView descriptionTextView = viewHolder.descriptionTextView;
        descriptionTextView.setText(step.shortDescription);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public interface OnItemClickListener {
        void onItemSelected(int position);
    }

}