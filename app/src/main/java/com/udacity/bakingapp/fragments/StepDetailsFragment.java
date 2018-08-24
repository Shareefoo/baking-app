package com.udacity.bakingapp.fragments;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Step;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepDetailsFragment extends Fragment {

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.imageView_thumbnail)
    ImageView imageViewThumbnail;

    @Nullable
    @BindView(R.id.textView_instruction)
    TextView instructionTextView;

    private SimpleExoPlayer player;

//    private Timeline.Window window;
//    private DataSource.Factory mediaDataSourceFactory;
//    private DefaultTrackSelector trackSelector;
//    private boolean shouldAutoPlay;
//    private BandwidthMeter bandwidthMeter;

    private String url;
//    private boolean isVideo = true;

    private String videoUrl;
    private String thumbnailUrl;
    private String description;

//    private long position = C.TIME_UNSET;
//    private int currentWindow;

    private long playbackPosition;
    private boolean playWhenReady;

    public static final String PLAYBACK_POSITION = "playback_position";
    public static final String PLAY_WHEN_READY = "play_when_ready";

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        //
        Bundle args = getArguments();

        if (args != null) {
            videoUrl = args.getString("video_url");
            thumbnailUrl = args.getString("thumbnail_url");
            description = args.getString("description");

            if (!TextUtils.isEmpty(videoUrl)) {
                url = videoUrl;
                simpleExoPlayerView.setVisibility(View.VISIBLE);

            } else if (!TextUtils.isEmpty(thumbnailUrl)) {
                url = thumbnailUrl;
                imageViewThumbnail.setVisibility(View.VISIBLE);

                Picasso.get()
                        .load(url)
                        .error(R.drawable.not_available)
                        .into(imageViewThumbnail);
            } else {
                imageViewThumbnail.setVisibility(View.VISIBLE);

                Picasso.get()
                        .load(R.drawable.not_available)
                        .into(imageViewThumbnail);
            }

            // check if portrait
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                instructionTextView.setText(description);
            }

            if (savedInstanceState != null) {
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            }

//            if (isVideo) {
//                shouldAutoPlay = true;
//                bandwidthMeter = new DefaultBandwidthMeter();
//                mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
//                window = new Timeline.Window();
//            }

        }

        return rootView;
    }

//    private void initializePlayer() {
//        if (isVideo) {
//            if (player == null) {
//                //
//                simpleExoPlayerView.requestFocus();
//
//                TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
//                player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
//
//                simpleExoPlayerView.setPlayer(player);
//
//                player.setPlayWhenReady(shouldAutoPlay);
//
//                Log.d("DDD", "releasePlayer: Index: " + player.getCurrentWindowIndex());
//
//                if (position != C.TIME_UNSET) {
//                    player.seekTo(position);
//                }
//
//                DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
//                        mediaDataSourceFactory, extractorsFactory, null, null);
//
//                player.prepare(mediaSource);
//            }
//        }
//    }

    private void initializePlayer(Uri uri) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        MediaSource mediaSource = buildMediaSource(uri);

        simpleExoPlayerView.setPlayer(player);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(playbackPosition);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

//    private void releasePlayer() {
//        if (isVideo) {
//            if (player != null) {
//                shouldAutoPlay = player.getPlayWhenReady();
//                position = player.getCurrentPosition();
//                currentWindow = player.getCurrentWindowIndex();
//                Log.d("DDD", "releasePlayer: Index: " + currentWindow);
//                player.release();
//                player = null;
//                trackSelector = null;
//            }
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        position = savedInstanceState.getLong("position");
//        shouldAutoPlay = savedInstanceState.getBoolean("auto_play");
//
//        Log.d("DDD", "onActivityCreated: Position: " + position);
//        Log.d("DDD", "onActivityCreated: Auto Play: " + shouldAutoPlay);
//    }

}
