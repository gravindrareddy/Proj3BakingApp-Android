package redgun.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import redgun.bakingapp.models.RecipeSteps;


/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener {
    ImageView recipe_step_imageview;
    SimpleExoPlayerView player;
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
        player = (SimpleExoPlayerView) rootView.findViewById(R.id.recipe_step_videoview);
        recipe_step_long_description_textview = (TextView) rootView.findViewById(R.id.recipe_step_long_description_textview);
        mContext = getActivity();

        recipeSteps = getArguments().getParcelable(getResources().getString(R.string.key_recipe_step_details_parcel));
        recipe_step_long_description_textview.setText(recipeSteps.getRecipeStepDescription());
        if (recipeSteps.getRecipeStepVideoURL() != null && recipeSteps.getRecipeStepVideoURL() != "") {
            recipe_step_imageview.setVisibility(View.GONE);
            //ToDo VideoView
        } else if (recipeSteps.getRecipeStepThumbnailURL() != null && recipeSteps.getRecipeStepThumbnailURL() != "") {
            player.setVisibility(View.GONE);
            //ToDo ImageView
        } else {
            recipe_step_imageview.setVisibility(View.GONE);
            player.setVisibility(View.GONE);
        }
        // ToDo - Simple step, fetch TextView and display, make both Image & Video as GONE
        // ToDO - Play Video & landscape mode on mobile in landscape


        return rootView;
    }

//    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
//        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
//                : Util.inferContentType("." + overrideExtension);
//        switch (type) {
//            case C.TYPE_SS:
//                return new SsMediaSource(uri, buildDataSourceFactory(false),
//                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
//            case C.TYPE_DASH:
//                return new DashMediaSource(uri, buildDataSourceFactory(false),
//                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
//            case C.TYPE_HLS:
//                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
//            case C.TYPE_OTHER:
//                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
//                        mainHandler, eventLogger);
//            default: {
//                throw new IllegalStateException("Unsupported type: " + type);
//            }
//        }
//    }
//
//    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid,
//                                                                           String licenseUrl, String[] keyRequestPropertiesArray) throws UnsupportedDrmException {
//        if (Util.SDK_INT < 18) {
//            return null;
//        }
//        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
//                buildHttpDataSourceFactory(false));
//        if (keyRequestPropertiesArray != null) {
//            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
//                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
//                        keyRequestPropertiesArray[i + 1]);
//            }
//        }
//        return new DefaultDrmSessionManager<>(uuid,
//                FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
//    }
//
//    private void releasePlayer() {
//        if (player != null) {
//            debugViewHelper.stop();
//            debugViewHelper = null;
//            shouldAutoPlay = player.getPlayWhenReady();
//            updateResumePosition();
//            player.release();
//            player = null;
//            trackSelector = null;
//            trackSelectionHelper = null;
//            eventLogger = null;
//        }
//    }
//
//    private void updateResumePosition() {
//        resumeWindow = player.getCurrentWindowIndex();
//        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
//                : C.TIME_UNSET;
//    }
//
//    private void clearResumePosition() {
//        resumeWindow = C.INDEX_UNSET;
//        resumePosition = C.TIME_UNSET;
//    }


//    private void initializePlayer() {
//        Intent intent = getIntent();
//        boolean needNewPlayer = player == null;
//        if (needNewPlayer) {
//            UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA)
//                    ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
//            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
//            if (drmSchemeUuid != null) {
//                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
//                String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
//                try {
//                    drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
//                            keyRequestPropertiesArray);
//                } catch (UnsupportedDrmException e) {
//                    int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported
//                            : (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
//                            ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
//                    showToast(errorStringId);
//                    return;
//                }
//            }
//
//            boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
//            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
//                    ((DemoApplication) getApplication()).useExtensionRenderers()
//                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
//                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
//                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
//            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this,
//                    drmSessionManager, extensionRendererMode);
//
//            TrackSelection.Factory adaptiveTrackSelectionFactory =
//                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
//            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
//            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
//            lastSeenTrackGroupArray = null;
//
//            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
//            player.addListener(this);
//
//            eventLogger = new EventLogger(trackSelector);
//            player.addListener(eventLogger);
//            player.setAudioDebugListener(eventLogger);
//            player.setVideoDebugListener(eventLogger);
//            player.setMetadataOutput(eventLogger);
//
//            simpleExoPlayerView.setPlayer(player);
//            player.setPlayWhenReady(shouldAutoPlay);
//            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
//            debugViewHelper.start();
//        }
//        if (needNewPlayer || needRetrySource) {
//            String action = intent.getAction();
//            Uri[] uris;
//            String[] extensions;
//            if (ACTION_VIEW.equals(action)) {
//                uris = new Uri[] {intent.getData()};
//                extensions = new String[] {intent.getStringExtra(EXTENSION_EXTRA)};
//            } else if (ACTION_VIEW_LIST.equals(action)) {
//                String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
//                uris = new Uri[uriStrings.length];
//                for (int i = 0; i < uriStrings.length; i++) {
//                    uris[i] = Uri.parse(uriStrings[i]);
//                }
//                extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);
//                if (extensions == null) {
//                    extensions = new String[uriStrings.length];
//                }
//            } else {
//                showToast(getString(R.string.unexpected_intent_action, action));
//                return;
//            }
//            if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
//                // The player will be reinitialized if the permission is granted.
//                return;
//            }
//            MediaSource[] mediaSources = new MediaSource[uris.length];
//            for (int i = 0; i < uris.length; i++) {
//                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
//            }
//            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
//                    : new ConcatenatingMediaSource(mediaSources);
//            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
//            if (haveResumePosition) {
//                player.seekTo(resumeWindow, resumePosition);
//            }
//            player.prepare(mediaSource, !haveResumePosition, false);
//            needRetrySource = false;
//            updateButtonVisibilities();
//        }
//    }

    private void initializePlayer(Uri mediaUri) {
// 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);


        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

//Set media controller
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();

// Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

// 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}
