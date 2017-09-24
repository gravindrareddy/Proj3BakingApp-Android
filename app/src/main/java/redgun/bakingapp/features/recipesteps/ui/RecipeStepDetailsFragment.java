package redgun.bakingapp.features.recipesteps.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.models.RecipeSteps;
import redgun.bakingapp.utilities.Utils;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;


/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener {
    ImageView recipe_step_imageview;
    SimpleExoPlayerView recipe_step_videoview;
    TextView recipe_step_long_description_textview;
    ArrayList<RecipeSteps> recipeStepsArrayList;
    RecipeSteps recipeStepDetails;
    int currentPosition;
    Context mContext;
    Button recipe_step_button_left_button, recipe_step_button_right_button;

    private SimpleExoPlayer player;
    private ExoPlayer.EventListener exoPlayerEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        recipe_step_imageview = (ImageView) rootView.findViewById(R.id.recipe_step_imageview);
        recipe_step_videoview = (SimpleExoPlayerView) rootView.findViewById(R.id.recipe_step_videoview);
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
        //creating the player
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(trackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

            //attaching the player to the view
            recipe_step_videoview.setUseController(true);
            recipe_step_videoview.requestFocus();
            recipe_step_videoview.setPlayer(player);
        }

        //preparing the player
        player.prepare(buildMediaSource(mediaUri));
        //player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri mediaUri) {
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.getResources().getString(R.string.app_name)), bandwidthMeterA);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(mediaUri, dataSourceFactory, extractorsFactory, null, null);
        return videoSource;
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.v(TAG, "Listener-onLoadingChanged...");
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.v(TAG, "Listener-onPlayerStateChanged...");
        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            Log.v(TAG, "Listener-onPlayerStateChanged...Playing");
        } else if (playbackState == ExoPlayer.STATE_READY) {
            Log.v(TAG, "Listener-onPlayerStateChanged...Paused");
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        Log.v(TAG, "Listener-onTimelineChanged...");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.v(TAG, "Listener-onPlayerError...");
        player.stop();
    }

    @Override
    public void onPositionDiscontinuity() {
        Log.v(TAG, "Listener-onPositionDiscontinuity...");
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    @Override
    public void onVisibilityChange(int visibility) {
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
