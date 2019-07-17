package redgun.bakingapp.features.recipestepsingridients.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import redgun.bakingapp.R;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.utilities.OnRecipeIngredientClickListener;


import static redgun.bakingapp.features.recipes.ui.RecipesActivity.mContext;

/**
 * Created by Ravindra on 29-05-2017.
 */


//todo: work on data flow due to flow change of recipe ingridients

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnRecipeStepClickListener, OnRecipeIngredientClickListener {
    Recipes intentReceivedRecipe;

    private boolean mTwoPane;
    private Bundle mSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSavedInstanceState = savedInstanceState;
        mContext = this;
        intentReceivedRecipe = getIntent().getExtras().getParcelable(getResources().getString(R.string.key_recipe_parcel));
        setContentView(R.layout.activity_recipe_steps);

        //this will enable Back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //For tablet
        if (findViewById(R.id.recipe_steps_twopane_relativelayout) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putParcelable(getResources().getString(R.string.key_recipe_parcel), intentReceivedRecipe);
                bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), intentReceivedRecipe.getRecipeIngredients());
                RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
                recipeIngredientsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.recipe_step_ingredients_or_details_fragment, recipeIngredientsFragment).commit();
            }
        }
        // For phone
        else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRecipeStepClicked(int position) {
        if (mTwoPane) {
            RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_step_details_parcel), intentReceivedRecipe.getRecipeSteps());
            bundle.putInt(getResources().getString(R.string.key_recipe_step_details_selected_position), position);
            recipeStepDetailsFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.recipe_step_ingredients_or_details_fragment, recipeStepDetailsFragment).commit();
            // This will populate the relevant content on right side fragment
        } else {
            // This will open the new view and populate relevant Activity with content
            Intent intent = new Intent(getApplicationContext(), RecipeStepAndIngridientDetailsActivity.class);
            intent.putExtra(getString(R.string.key_recipe_steps_or_ingredients), getString(R.string.const_recipes));
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_step_details_parcel), intentReceivedRecipe.getRecipeSteps());
            bundle.putInt(getResources().getString(R.string.key_recipe_step_details_selected_position), position);
            intent.putExtra(getResources().getString(R.string.key_recipe_step_details_bundle), bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onRecipeIngredientClicked() {
        if (mTwoPane) {
            RecipeIngredientsFragment recipeIngredientFragment = new RecipeIngredientsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), intentReceivedRecipe.getRecipeIngredients());
            recipeIngredientFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.recipe_step_ingredients_or_details_fragment, recipeIngredientFragment).commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), RecipeStepAndIngridientDetailsActivity.class);
            intent.putExtra(getString(R.string.key_recipe_steps_or_ingredients), getString(R.string.const_ingredients));
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), intentReceivedRecipe.getRecipeIngredients());
            intent.putExtra(getResources().getString(R.string.key_recipe_ingredients_bundle), bundle);
            startActivity(intent);
        }
    }
}
