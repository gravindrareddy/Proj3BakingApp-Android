package redgun.bakingapp.features.recipestepsingridients.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.models.RecipeSteps;
import redgun.bakingapp.utilities.Utils;


/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsFragment extends Fragment{
    ImageView recipe_step_imageview;
    PlayerView recipe_step_videoview;
    TextView recipe_step_long_description_textview;
    ArrayList<RecipeSteps> recipeStepsArrayList;
    RecipeSteps recipeStepDetails;
    int currentPosition;
    Context mContext;
    Button recipe_step_button_left_button, recipe_step_button_right_button;
    String TAG="RecipeStepDetailsFragment";

    boolean playWhenReady;
    int currentWindow;
    long playbackPosition;




    private SimpleExoPlayer player;
    private ExoPlayer.EventListener exoPlayerEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);

        recipe_step_imageview = (ImageView) rootView.findViewById(R.id.recipe_step_imageview);
        recipe_step_videoview = (PlayerView) rootView.findViewById(R.id.recipe_step_videoview);
        recipe_step_long_description_textview = (TextView) rootView.findViewById(R.id.recipe_step_long_description_textview);
        recipe_step_button_right_button = (Button) rootView.findViewById(R.id.recipe_step_button_right_button);
        recipe_step_button_left_button = (Button) rootView.findViewById(R.id.recipe_step_button_left_button);

        mContext = getActivity();
        if (getArguments() != null) {
            recipeStepsArrayList = getArguments().getParcelableArrayList(getResources().getString(R.string.key_recipe_step_details_parcel));
            currentPosition = getArguments().getInt(getResources().getString(R.string.key_recipe_step_details_selected_position));
            recipeStepDetails = recipeStepsArrayList.get(currentPosition);
            populateContent();
        }
        // ToDO - Play Video without RESUME on change in orientation


        recipe_step_button_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0) {
                    currentPosition--;
                    recipeStepDetails = recipeStepsArrayList.get(currentPosition);
                    populateContent();
                } else {
                    Utils.showToast(mContext, "This is the first step");
                }
            }
        });


        recipe_step_button_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < (recipeStepsArrayList.size() - 1)) {
                    currentPosition++;
                    recipeStepDetails = recipeStepsArrayList.get(currentPosition);
                    populateContent();
                } else{
                    Utils.showToast(mContext, "This is the last step");
                }
            }
        });


        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext.getResources().getConfiguration().orientation == mContext.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            recipe_step_videoview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        } else {
            recipe_step_videoview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT

            );
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


    private void initializePlayer(Uri mediaUri) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(), new DefaultLoadControl());

        recipe_step_videoview.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);


        //Uri uri = Uri.parse(recipeStepDetails.getRecipeStepVideoURL());
        MediaSource mediaSource = buildMediaSource(mediaUri);
        player.prepare(mediaSource, true, false);
    }



    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(mContext, mContext.getResources().getString(R.string.app_name)))).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }


    /**
     * display content on view
     */
    void populateContent() {
        recipe_step_long_description_textview.setText(recipeStepDetails.getRecipeStepDescription());
        if (recipeStepDetails.getRecipeStepVideoURL() != null && !recipeStepDetails.getRecipeStepVideoURL().equals("")) {
            //if(false){
            recipe_step_videoview.setVisibility(View.VISIBLE);
            recipe_step_imageview.setVisibility(View.GONE);
            initializePlayer(Uri.parse(recipeStepDetails.getRecipeStepVideoURL()));
        } else {
            recipe_step_videoview.setVisibility(View.GONE);
            releasePlayer();
            recipe_step_imageview.setVisibility(View.VISIBLE);
        }
    }
}
