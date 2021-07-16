package com.example.videostreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoard extends AppCompatActivity {
    FloatingActionButton addVideo;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // fetch data from firebase database
        FirebaseRecyclerOptions<FileModel> options =
                new FirebaseRecyclerOptions.Builder<FileModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("videos"), FileModel.class)
                        .build();


        // creating Firebase Recycler Adapter

        FirebaseRecyclerAdapter<FileModel,ViewHolder> adapter =
                new FirebaseRecyclerAdapter<FileModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  ViewHolder holder, int position, @NonNull FileModel model) {
                holder.prepareExoplayer(getApplication(),model.getTitle(),model.getVideoUrl());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
                return new ViewHolder(view);
            }
        };

        addVideo = (FloatingActionButton) findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,AddVideo.class));
            }
        });

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}