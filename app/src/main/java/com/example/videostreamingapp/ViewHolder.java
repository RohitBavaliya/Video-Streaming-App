package com.example.videostreamingapp;

import android.app.Application;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;


public class ViewHolder extends RecyclerView.ViewHolder {
    TextView titleVideo;
    // creating a variable for simpleExoPlayerView.
    SimpleExoPlayerView simpleExoPlayerView;
    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;
    ImageView like;
    TextView count;
    DatabaseReference databaseReference;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleVideo = (TextView) itemView.findViewById(R.id.titleVideo);
        simpleExoPlayerView = itemView.findViewById(R.id.idSimpleExoPlayerVIew);
        like = (ImageView) itemView.findViewById(R.id.like);
        count = (TextView) itemView.findViewById(R.id.count);
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


    // like function
    public void getLikeStatus(final String postId,final String userId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("likes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postId).hasChild(userId))
                {
                    int count_like = (int) snapshot.child(postId).getChildrenCount();
                    count.setText(count_like+" likes");
                    like.setImageResource(R.drawable.like);
                }
                else
                {
                    int count_like = (int)snapshot.child(postId).getChildrenCount();
                    count.setText(count_like+" likes");
                    like.setImageResource(R.drawable.dislike);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
