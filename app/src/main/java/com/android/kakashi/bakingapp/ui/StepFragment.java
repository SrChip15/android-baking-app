package com.android.kakashi.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {
    
    @BindView(R.id.step_description_tv)
    TextView stepDescriptionTextView;
    @BindView(R.id.step_video_player)
    PlayerView playerView;
    private ExoPlayer player;
    private Step step;
    
    private static final String ARG_STEP = "step";
    
    public static StepFragment newInstance(Step currentStep) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, currentStep);
        
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(ARG_STEP);
        } else {
            throw new IllegalArgumentException("Expected step argument missing!");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setVideoPlayer();
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            setVideoPlayer();
        }
    }
    
    private void setVideoPlayer() {
        if (step.getVideoURL().length() > 0) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        } else {
            // video url absent; remove player from view
            playerView.setVisibility(View.GONE);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_step, container, false);
        ButterKnife.bind(this, view);
        
        ImageButton replayButton = playerView.findViewById(R.id.exo_replay);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(0);
                player.setPlayWhenReady(true);
            }
        });
        
        String description = step.getDescription();
        stepDescriptionTextView.setText(description);
        
        return view;
    }
    
    private void initializePlayer() {
        if (player == null) {
            
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            
            player = ExoPlayerFactory.newSimpleInstance(
                    renderersFactory,
                    trackSelector,
                    loadControl
            );
    
            playerView.setPlayer(player);
            playerView.setControllerShowTimeoutMs(1000);
    
            MediaSource mediaSource = buildMediaSource();
            
            player.prepare(mediaSource);
            
        }
    }
    
    private MediaSource buildMediaSource() {
        Uri uri = Uri.parse(step.getVideoURL());
        
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("BakingApp"))
                .createMediaSource(uri);
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
    
    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
