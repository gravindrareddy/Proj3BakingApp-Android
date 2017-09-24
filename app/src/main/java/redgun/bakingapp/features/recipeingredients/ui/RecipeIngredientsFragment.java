package redgun.bakingapp.features.recipeingredients.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipeingredients.adapter.RecipeIngredientListAdapter;
import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.RecipeSteps;
import redgun.bakingapp.models.Recipes;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeIngredientsFragment extends Fragment {


    RecyclerView recipe_ingridients_recyclerview;
    ArrayList<RecipeIngredients> recipeIngredientsesList;
    private boolean mTwoPane;

    public RecipeIngredientsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        recipe_ingridients_recyclerview = (RecyclerView) rootView.findViewById(R.id.recipe_ingridients_recyclerview);

        if (getArguments() != null) {
            recipeIngredientsesList = getArguments().getParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel));
            RecipeIngredientListAdapter recipeIngredientListAdapter = new RecipeIngredientListAdapter(recipeIngredientsesList);
            recipe_ingridients_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            recipe_ingridients_recyclerview.setItemAnimator(new DefaultItemAnimator());
            recipe_ingridients_recyclerview.setAdapter(recipeIngredientListAdapter);
        } else {

        }

        return rootView;
    }

//    /*
//   RiskStepsActivity call this method to populate data on Right Fragment for Tablet
//    */
//    public void displayIngredients(ArrayList<RecipeIngredients> mRecipeIngredients) {
//        //todo: segregate common code to different function
//        RecipeIngredientListAdapter recipeIngredientListAdapter = new RecipeIngredientListAdapter(mRecipeIngredients);
//        recipe_ingridients_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recipe_ingridients_recyclerview.setItemAnimator(new DefaultItemAnimator());
//        recipe_ingridients_recyclerview.setAdapter(recipeIngredientListAdapter);
//    }
}
