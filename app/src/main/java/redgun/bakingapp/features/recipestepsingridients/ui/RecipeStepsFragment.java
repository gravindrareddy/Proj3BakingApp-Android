package redgun.bakingapp.features.recipestepsingridients.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import redgun.bakingapp.utilities.OnRecipeIngredientClickListener;
import redgun.bakingapp.R;
import redgun.bakingapp.features.recipestepsingridients.adapter.RecipeIngredientListAdapter;
import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.features.recipestepsingridients.adapter.RecipeStepsListAdapter;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepsFragment extends Fragment {

    RecyclerView recipe_steps_recyclerview;
    TextView recipe_ingredient_textview;
    Recipes recipes;
    ArrayList<RecipeIngredients> recipeIngredientsesList;
    private boolean mTwoPane;
    OnRecipeStepClickListener mRecipeStepCallback;
    OnRecipeIngredientClickListener mOnRecipeIngredientClickListener;

    public RecipeStepsFragment() {

    }


    public interface OnRecipeStepClickListener {
        void onRecipeStepClicked(int position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRecipeStepCallback = (OnRecipeStepClickListener) context;
            mOnRecipeIngredientClickListener = (OnRecipeIngredientClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        recipe_steps_recyclerview = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recyclerview);
        recipe_ingredient_textview = (TextView) rootView.findViewById(R.id.recipe_ingredient_textview);


        recipe_ingredient_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecipeIngredientClickListener.onRecipeIngredientClicked();
            }
        });

        RecipeStepsActivity rs = (RecipeStepsActivity) getActivity();
        recipes = rs.intentReceivedRecipe;
        RecipeStepsListAdapter recipeStepListAdapter = new RecipeStepsListAdapter(recipes.getRecipeSteps());
        recipeStepListAdapter.setOnItemClickListener(mRecipeStepCallback);
        recipe_steps_recyclerview.setAdapter(recipeStepListAdapter);
        recipe_steps_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecipeIngredientListAdapter recipeIngredientListAdapter = new RecipeIngredientListAdapter(recipes.getRecipeIngredients());
        return rootView;
    }
}
