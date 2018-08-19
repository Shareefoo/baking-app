package com.udacity.bakingapp.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.udacity.bakingapp.fragments.StepDetailsFragment;
import com.udacity.bakingapp.models.Step;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

//    @BindView(R.id.viewPager)
//    ViewPager viewPager;
//
//    StepPagerAdapter pagerAdapter;

    private Step step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        //
        step = (Step) Parcels.unwrap(getIntent().getParcelableExtra("step"));

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("video_url", step.videoUrl);
        bundle.putString("thumbnail_url", step.thumbnailUrl);
        bundle.putString("description", step.description);

        stepDetailsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_details_fragment_container, stepDetailsFragment)
                .commit();

    }

//    public class StepPagerAdapter extends FragmentStatePagerAdapter {
//
//        public StepPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Fragment fragment = new StepDetailsFragment();
//            Bundle args = new Bundle();
//            args.putString("video_url", step.videoUrl);
//            args.putString("thumbnail_url", step.thumbnailUrl);
//            args.putString("description", step.description);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return 100;
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "Step " + (position + 1);
//        }
//    }
//
//    }

}
