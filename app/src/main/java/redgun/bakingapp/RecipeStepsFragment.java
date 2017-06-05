package redgun.bakingapp;

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

import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.Recipes;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepsFragment extends Fragment implements OnRecyclerViewItemClickListener {

    RecyclerView recipe_steps_recyclerview;
    RecyclerView recipe_ingridients_recyclerview;
    Recipes recipes;
    ArrayList<RecipeIngredients> recipeIngredientsesList;

    OnRecipeStepClickListener mOnRecipeStepClickListener;


//    public static RecipeStepsFragment newInstance(Bundle args) {
//        RecipeStepsFragment myFragment = new RecipeStepsFragment();
//        myFragment.setArguments(args);
//        return myFragment;
//    }

    public RecipeStepsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnRecipeStepClickListener = (OnRecipeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        recipe_steps_recyclerview = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recyclerview);
        recipe_ingridients_recyclerview = (RecyclerView) rootView.findViewById(R.id.recipe_ingridients_recyclerview);

        if (getArguments() != null) {
            recipes = getArguments().getParcelable(getResources().getString(R.string.key_recipe_parcel));
            RecipeIngredientListAdapter recipeIngredientListAdapter = new RecipeIngredientListAdapter(recipes.getRecipeIngredients());
            recipe_ingridients_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            recipe_ingridients_recyclerview.setItemAnimator(new DefaultItemAnimator());
            recipe_ingridients_recyclerview.setAdapter(recipeIngredientListAdapter);
            RecipeStepsListAdapter recipeStepListAdapter = new RecipeStepsListAdapter(recipes.getRecipeSteps());
            recipeStepListAdapter.setOnItemClickListener(mOnRecipeStepClickListener);
            recipe_steps_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            recipe_steps_recyclerview.setAdapter(recipeStepListAdapter);
        } else {

        }

        return rootView;
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        mOnRecipeStepClickListener.
                onRecipeStepClicked(position);
    }

    public interface OnRecipeStepClickListener {
        void onRecipeStepClicked(int position);
    }
}
