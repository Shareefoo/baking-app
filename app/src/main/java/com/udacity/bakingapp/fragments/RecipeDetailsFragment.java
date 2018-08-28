package com.udacity.bakingapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.udacity.bakingapp.adapters.IngredientAdapter;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapters.StepAdapter;
import com.udacity.bakingapp.data.SpManager;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.models.Step;
import com.udacity.bakingapp.widget.IngredientsWidgetProvider;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements StepAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView_ingredients)
    RecyclerView mRecyclerViewIngredients;

    @BindView(R.id.recyclerView_steps)
    RecyclerView mRecyclerViewSteps;

    @BindView(R.id.textView_ingredients_fold)
    TextView mTextViewIngredientsFold;

    @BindView(R.id.textView_ingredients_unfold)
    TextView mTextViewIngredientsUnfold;

    @BindView(R.id.folding_cell)
    FoldingCell mFoldingCell;

    List<Step> mSteps;

    OnStepClickListener mCallback;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        mTextViewIngredientsFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFoldingCell.fold(false);
            }
        });

        mTextViewIngredientsUnfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFoldingCell.unfold(false);
            }
        });

//        foldingCell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foldingCell.toggle(false);
//            }
//        });


        Recipe recipe = (Recipe) Parcels.unwrap(getArguments().getParcelable("recipe"));

        SpManager spManager = SpManager.getInstance(getContext());
        spManager.putInt("last_visited_recipe", recipe.id);

//        Recipe recipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        List<Ingredient> ingredients = recipe.ingredients;
        mSteps = recipe.steps;

        Intent intent = new Intent(getContext(), IngredientsWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(), IngredientsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getContext().sendBroadcast(intent);

        //
        RecyclerView.LayoutManager ingredientLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewIngredients.setHasFixedSize(true);
        mRecyclerViewIngredients.setLayoutManager(ingredientLayoutManager);
        RecyclerView.Adapter ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        mRecyclerViewIngredients.setAdapter(ingredientAdapter);

        //
        RecyclerView.LayoutManager stepLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setLayoutManager(stepLayoutManager);
        RecyclerView.Adapter stepAdapter = new StepAdapter(getActivity(), mSteps, this);
        mRecyclerViewSteps.setAdapter(stepAdapter);

        return rootView;
    }

    @Override
    public void onItemSelected(int position) {
        Step step = mSteps.get(position);
        mCallback.onStepClicked(step);
    }

    public interface OnStepClickListener {
        void onStepClicked(Step step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }

}
