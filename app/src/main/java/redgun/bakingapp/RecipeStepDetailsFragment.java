package redgun.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
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

import redgun.bakingapp.models.RecipeSteps;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;


/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener {
    ImageView recipe_step_imageview;
    SimpleExoPlayerView recipe_step_videoview;
    TextView recipe_step_long_description_textview;
    RecipeSteps recipeSteps;
    Context mContext;

    private SimpleExoPlayer player;
    private ExoPlayer.EventListener exoPlayerEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        recipe_step_imageview = (ImageView) rootView.findViewById(R.id.recipe_step_imageview);
        recipe_step_videoview = (SimpleExoPlayerView) rootView.findViewById(R.id.recipe_step_videoview);
        recipe_step_long_description_textview = (TextView) rootView.findViewById(R.id.recipe_step_long_description_textview);
        mContext = getActivity();

        recipeSteps = getArguments().getParcelable(getResources().getString(R.string.key_recipe_step_details_parcel));
        recipe_step_long_description_textview.setText(recipeSteps.getRecipeStepDescription());
        if (recipeSteps.getRecipeStepVideoURL() != null && recipeSteps.getRecipeStepVideoURL() != "") {
            //if(false){
            recipe_step_imageview.setVisibility(View.GONE);
            initializePlayer(Uri.parse(recipeSteps.getRecipeStepVideoURL()));
        } else if (recipeSteps.getRecipeStepThumbnailURL() != null && recipeSteps.getRecipeStepThumbnailURL() != "") {
            recipe_step_videoview.setVisibility(View.GONE);
            releasePlayer();
            // ToDo ImageView
        } else {
            recipe_step_imageview.setVisibility(View.GONE);
            recipe_step_videoview.setVisibility(View.GONE);
        }

        // ToDO - Play Video & landscape mode on mobile in landscape


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
}
