package redgun.bakingapp.features.recipeingredients.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipesteps.ui.RecipeStepDetailsFragment;
import redgun.bakingapp.models.RecipeIngredients;

import static redgun.bakingapp.features.recipes.ui.RecipesActivity.mContext;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeIngredientsActivity extends AppCompatActivity {
    private Bundle mSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        Intent i = getIntent();

        //this will enable Back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecipeIngredientsFragment mRecipeIngredientsFragment = new RecipeIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getResources().getString(R.string.key_recipe_ingredients_parcel), i.getParcelableArrayListExtra(getResources().getString(R.string.key_recipe_ingredients_parcel)));
        mRecipeIngredientsFragment.setArguments(bundle);

        //todo: get from savedInstanceState
        if (mSavedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_ingredients_fragment, mRecipeIngredientsFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_ingredients_fragment, mRecipeIngredientsFragment).commit();
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
