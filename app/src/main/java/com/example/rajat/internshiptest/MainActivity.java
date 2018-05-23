package com.example.rajat.internshiptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnListDisplayListener, OnItemClickListener {
    private static final String SONG_DETAILS = "songDetails";
    private RecyclerView songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsList = findViewById(R.id.songs_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        songsList.setLayoutManager(layoutManager);

        SongDetailsTask songDetailsTask = new SongDetailsTask(this);
        songDetailsTask.execute();
    }

    @Override
    public void setSongDetailsList(ArrayList<SongDetails> songDetailsList) {
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(songDetailsList, this);
        songsList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(SongDetails songDetails) {
        Intent intent = new Intent(this, SongDetailsActivity.class);
        intent.putExtra(SONG_DETAILS, songDetails);
        startActivity(intent);
    }
}