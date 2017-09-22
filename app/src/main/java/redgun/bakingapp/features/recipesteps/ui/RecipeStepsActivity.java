package redgun.bakingapp.features.recipesteps.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import redgun.bakingapp.R;
import redgun.bakingapp.models.Recipes;


import static redgun.bakingapp.features.recipes.ui.RecipesActivity.mContext;

/**
 * Created by Ravindra on 29-05-2017.
 */


//todo: work on data flow due to flow change of recipe ingridients

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnRecipeStepClickListener {
    Recipes intentReceivedRecipe;
    Fragment recipeStepsFragment;
    Fragment recipeStepDetailsFragment;

    private boolean mTwoPane;
    private Bundle mSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        Intent i = getIntent();
        intentReceivedRecipe = i.getExtras().getParcelable(getResources().getString(R.string.key_recipe_parcel));

        //ToDo - Send only the relevant recipe ingredients.. Should be able to send Parcelable array
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelable(getResources().getString(R.string.key_recipe_parcel), intentReceivedRecipe);
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.recipe_steps_fragment, recipeStepsFragment).commit();
        }

        if (findViewById(R.id.recipe_step_details_fragment_relative_layout) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                recipeStepDetailsFragment = fragmentManager.findFragmentById(R.id.recipe_step_details_fragment);
                fragmentManager.beginTransaction().add(R.id.recipe_step_details_fragment, recipeStepDetailsFragment).commit();
            }
        } else {
            mTwoPane = false;
        }


    }


    @Override
    public void onRecipeStepClicked(int position) {
        //ToDo - pass information to Step Details Fragment about position
        Bundle bundle = new Bundle();
        bundle.putParcelable(getResources().getString(R.string.key_recipe_step_details_parcel), intentReceivedRecipe.getRecipeSteps().get(position));
        if (mTwoPane) {
            if (mSavedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                recipeStepDetailsFragment = fragmentManager.findFragmentById(R.id.recipe_step_details_fragment);
                RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
                recipeStepDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.recipe_step_details_fragment, recipeStepDetailsFragment).commit();
            } else {
                RecipeStepDetailsFragment newRecipeStepDetailsFragment = new RecipeStepDetailsFragment();
                recipeStepDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_step_details_fragment, newRecipeStepDetailsFragment).commit();
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), RecipeStepDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.key_recipe_step_details_bundle), bundle);
            startActivity(intent);
        }
    }
}
