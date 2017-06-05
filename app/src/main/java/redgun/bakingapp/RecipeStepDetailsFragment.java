package redgun.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import redgun.bakingapp.models.RecipeSteps;
import redgun.bakingapp.models.Recipes;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsFragment extends Fragment {
    ImageView recipe_step_imageview;
    VideoView recipe_step_videoview;
    TextView recipe_step_long_description_textview;
    RecipeSteps recipeSteps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        recipe_step_imageview = (ImageView) rootView.findViewById(R.id.recipe_step_imageview);
        recipe_step_videoview = (VideoView) rootView.findViewById(R.id.recipe_step_videoview);
        recipe_step_long_description_textview = (TextView) rootView.findViewById(R.id.recipe_step_long_description_textview);
        //ToDo - Set appropriate visibility
        recipe_step_imageview.setVisibility(View.GONE);
        recipe_step_videoview.setVisibility(View.GONE);

        recipeSteps = getArguments().getParcelable(getResources().getString(R.string.key_recipe_step_details_parcel));
        recipe_step_long_description_textview.setText(recipeSteps.getRecipeStepDescription());
        // ToDo - Simple step, fetch TextView and display, make both Image & Video as GONE
        // ToDO - Play Video & landscape mode on mobile in landscape


        return rootView;
    }
}
