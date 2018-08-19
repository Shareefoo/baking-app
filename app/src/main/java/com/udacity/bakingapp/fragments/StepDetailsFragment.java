package com.udacity.bakingapp.fragments;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Step;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepDetailsFragment extends Fragment {

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    @Nullable
    @BindView(R.id.textView_instruction)
    TextView instructionTextView;

    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    private String url;
    private boolean isVideo = true;

    private String videoUrl;
    private String thumbnailUrl;
    private String description;

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


            if (!videoUrl.isEmpty()) {
                url = videoUrl;

            } else if (!thumbnailUrl.isEmpty()) {
                url = thumbnailUrl;

            } else {
                simpleExoPlayerView.setVisibility(View.GONE);

                isVideo = false;
            }

            // check if portrait
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                instructionTextView.setText(description);
            }

            if (isVideo) {
                shouldAutoPlay = true;
                bandwidthMeter = new DefaultBandwidthMeter();
                mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
                window = new Timeline.Window();
            }

        }

        return rootView;
    }

    @OnClick(R.id.imageButton_previous)
    public void previousStep(View view) {

    }

    @OnClick(R.id.imageButton_previous)
    public void nextStep(View view) {

    }

    private void initializePlayer() {
        if (isVideo) {
            //
            simpleExoPlayerView.requestFocus();

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            simpleExoPlayerView.setPlayer(player);

            player.setPlayWhenReady(shouldAutoPlay);

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                    mediaDataSourceFactory, extractorsFactory, null, null);

            player.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        if (isVideo) {
            if (player != null) {
                shouldAutoPlay = player.getPlayWhenReady();
                player.release();
                player = null;
                trackSelector = null;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
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

}
