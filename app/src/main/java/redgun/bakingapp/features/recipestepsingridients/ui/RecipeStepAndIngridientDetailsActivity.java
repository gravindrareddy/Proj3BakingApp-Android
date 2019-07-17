package redgun.bakingapp.features.recipestepsingridients.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import redgun.bakingapp.R;

import static redgun.bakingapp.features.recipes.ui.RecipesActivity.mContext;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepAndIngridientDetailsActivity extends AppCompatActivity {
    Fragment recipeStepDetailsFragment;
    private boolean mTwoPane;
    private Bundle mSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details_or_ingridients);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        Intent mIntent = getIntent();

        //this will enable Back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String recipesteporingredient = mIntent.getStringExtra(getResources().getString(R.string.key_recipe_steps_or_ingredients));


        if (mSavedInstanceState == null) {
            if (recipesteporingredient.equals(getString(R.string.const_recipes))) { // Inflate Recipe steps
                Bundle intentReceivedRecipeStepDetails = mIntent.getExtras().getBundle(getResources().getString(R.string.key_recipe_step_details_bundle));
                RecipeStepDetailsFragment newRecipeStepDetailsFragment = new RecipeStepDetailsFragment();
                newRecipeStepDetailsFragment.setArguments(intentReceivedRecipeStepDetails);
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_step_ingredients_or_details_fragment, newRecipeStepDetailsFragment).commit();
            } else if (recipesteporingredient.equals(getString(R.string.const_ingredients))) { // Inflate Recipe Ingridients
                RecipeIngredientsFragment mRecipeIngredientsFragment = new RecipeIngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), mIntent.getExtras().getBundle(getResources().getString(R.string.key_recipe_ingredients_bundle)).getParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel)));
                mRecipeIngredientsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_step_ingredients_or_details_fragment, mRecipeIngredientsFragment).commit();
            }
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
}
