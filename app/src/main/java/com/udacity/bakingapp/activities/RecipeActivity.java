package com.udacity.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ramotion.foldingcell.FoldingCell;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.fragments.RecipeDetailsFragment;
import com.udacity.bakingapp.fragments.StepDetailsFragment;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.models.Step;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            recipeDetailsFragment.setArguments(getIntent().getExtras());

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_details_fragment_container, recipeDetailsFragment)
                    .commit();

            // Determine if you're creating a two-pane or single-pane display
            if (findViewById(R.id.tablet_layout) != null) {
                // This LinearLayout will only initially exist in the two-pane tablet case
                mTwoPane = true;

//                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
//                fragmentManager.beginTransaction()
//                        .add(R.id.step_details_fragment_container, stepDetailsFragment)
//                        .commit();

            } else {
                // We're in single-pane mode and displaying fragments on a phone in separate activities
                mTwoPane = false;
            }

        }

    }

    @Override
    public void onStepClicked(Step step) {

        if (mTwoPane) {

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

            Bundle bundle = new Bundle();
            bundle.putString("video_url", step.videoURL);
            bundle.putString("thumbnail_url", step.thumbnailURL);
            bundle.putString("description", step.description);

            stepDetailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_fragment_container, stepDetailsFragment)
                    .commit();

        } else {
            Intent recipeDetailsIntent = new Intent(this, StepDetailsActivity.class);
            recipeDetailsIntent.putExtra("step", Parcels.wrap(step));
            startActivity(recipeDetailsIntent);
        }

    }
}
