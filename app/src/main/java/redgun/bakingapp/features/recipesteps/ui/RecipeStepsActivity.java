package redgun.bakingapp.features.recipesteps.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipeingredients.ui.RecipeIngredientsActivity;
import redgun.bakingapp.features.recipeingredients.ui.RecipeIngredientsFragment;
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
        setContentView(R.layout.activity_recipe_steps);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        Intent i = getIntent();
        intentReceivedRecipe = i.getExtras().getParcelable(getResources().getString(R.string.key_recipe_parcel));

        //this will enable Back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //The below is default for both phones (complete view) and tablet (left view)
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelable(getResources().getString(R.string.key_recipe_parcel), intentReceivedRecipe);
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.recipe_steps_fragment, recipeStepsFragment).commit();
        }


        if (findViewById(R.id.recipe_steps_twopane_relativelayout) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                //todo - tablet view. fill the fragments and prepare the view
                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), intentReceivedRecipe.getRecipeIngredients());
                RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
                recipeIngredientsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.recipe_step_ingredients_or_details_fragment, recipeIngredientsFragment).commit();
            }
        } else {
            //todo - phone view.
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


//    @Override
//    public void onRecipeStepClicked(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(getResources().getString(R.string.key_recipe_step_details_parcel), intentReceivedRecipe.getRecipeSteps().get(position));
//        if (mTwoPane) {
//            if (mSavedInstanceState == null) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                recipeStepDetailsFragment = fragmentManager.findFragmentById(R.id.recipe_step_details_fragment);
//                RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
//                recipeStepDetailsFragment.setArguments(bundle);
//                fragmentManager.beginTransaction().add(R.id.recipe_step_details_fragment, recipeStepDetailsFragment).commit();
//            } else {
//                RecipeStepDetailsFragment newRecipeStepDetailsFragment = new RecipeStepDetailsFragment();
//                recipeStepDetailsFragment.setArguments(bundle);
//                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_step_details_fragment, newRecipeStepDetailsFragment).commit();
//            }
//        } else {
//            Intent intent = new Intent(getApplicationContext(), RecipeStepDetailsActivity.class);
//            intent.putExtra(getResources().getString(R.string.key_recipe_step_details_bundle), bundle);
//            startActivity(intent);
//        }
//    }


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
            //recipeStepDetailsFragment.displayStepDetails(intentReceivedRecipe.getRecipeSteps().get(position));
        } else {
            // This will open the new view and populate relevant Activity with content
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_step_details_parcel), intentReceivedRecipe.getRecipeSteps());
            bundle.putInt(getResources().getString(R.string.key_recipe_step_details_selected_position), position);
            Intent intent = new Intent(getApplicationContext(), RecipeStepDetailsActivity.class);
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
            // This will populate the relevant content on right side fragment
            //recipeIngredientFragment.displayIngredients(intentReceivedRecipe.getRecipeIngredients());
        } else {
            // This will open the new view and populate relevant Activity with content
            Intent intent = new Intent(getApplicationContext(), RecipeIngredientsActivity.class);
            intent.putParcelableArrayListExtra(getResources().getString(R.string.key_recipe_ingredients_parcel), intentReceivedRecipe.getRecipeIngredients());
            startActivity(intent);
        }
    }
}
