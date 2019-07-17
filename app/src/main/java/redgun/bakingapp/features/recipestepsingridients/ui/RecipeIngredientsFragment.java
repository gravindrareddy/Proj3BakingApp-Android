package redgun.bakingapp.features.recipestepsingridients.ui;

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
import redgun.bakingapp.features.recipestepsingridients.adapter.RecipeIngredientListAdapter;
import redgun.bakingapp.models.RecipeIngredients;

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
            recipeIngredientsesList = getArguments().getParcelableArrayList(getString(R.string.key_recipe_ingredients_parcel));
            RecipeIngredientListAdapter recipeIngredientListAdapter = new RecipeIngredientListAdapter(recipeIngredientsesList);
            recipe_ingridients_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            recipe_ingridients_recyclerview.setItemAnimator(new DefaultItemAnimator());
            recipe_ingridients_recyclerview.setAdapter(recipeIngredientListAdapter);
        }
        return rootView;
    }
}
