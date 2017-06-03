package redgun.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import static redgun.bakingapp.RecipesActivity.mContext;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsActivity extends AppCompatActivity {
    Fragment recipeStepDetailsFragment;
    private boolean mTwoPane;
    private Bundle mSavedInstanceState;
    private Bundle intentReceivedRecipeStepDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        Intent i = getIntent();
        intentReceivedRecipeStepDetails = i.getExtras().getBundle(getResources().getString(R.string.key_recipe_step_details_bundle));
        FragmentManager fragmentManager = getSupportFragmentManager();
        recipeStepDetailsFragment = fragmentManager.findFragmentById(R.id.recipe_step_details_fragment);
        recipeStepDetailsFragment.setArguments(intentReceivedRecipeStepDetails);
    }
}
