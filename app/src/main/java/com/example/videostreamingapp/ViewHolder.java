package com.example.videostreamingapp;

import android.app.Application;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewHolder extends RecyclerView.ViewHolder {
    TextView titleVideo;
    // creating a variable for simpleExoPlayerView.
    SimpleExoPlayerView simpleExoPlayerView;
    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleVideo = (TextView) itemView.findViewById(R.id.titleVideo);
        simpleExoPlayerView = itemView.findViewById(R.id.idSimpleExoPlayerVIew);
    }

    public void prepareExoplayer(Application application,String videoTitle,String videoUrl)
    {
        try {
            titleVideo.setText(videoTitle);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer =(SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application,trackSelector);

            Uri videoURI = Uri.parse(videoUrl);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);


        }
        catch (Exception e)
        {
            Toast.makeText(application, "ExoPlayer Crahsed!!"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
