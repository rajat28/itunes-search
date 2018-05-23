package com.example.rajat.internshiptest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SongDetails> songDetailsList;
    private OnItemClickListener onItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView songNameText, collectionNameText, songTimeText;
        private ImageView songArtworkImage;

        public ViewHolder(View view) {
            super(view);

            songNameText = view.findViewById(R.id.song_name_text_view);
            songArtworkImage = view.findViewById(R.id.song_artwork_image_view);
            collectionNameText = view.findViewById(R.id.collection_name_text_view);
            songTimeText = view.findViewById(R.id.song_time_text_view);
        }

        public void setItem(final SongDetails songDetails, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(songDetails);
                }
            });
        }
    }

    public CustomRecyclerViewAdapter(ArrayList<SongDetails> songDetailsList, OnItemClickListener onItemClickListener) {
        this.songDetailsList = songDetailsList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler_view_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongDetails songDetails = songDetailsList.get(position);

        holder.songArtworkImage.setImageDrawable(null);
        holder.songArtworkImage.setTag(songDetails.getImageURL());
        DownloadImageTask downloadImageTask = new DownloadImageTask(songDetails.getImageURL(), holder.songArtworkImage);
        downloadImageTask.execute();

        holder.songNameText.setText(songDetails.getSong());
        holder.collectionNameText.setText((songDetails.getCollection() != null) ? songDetails.getCollection() : "");

        Long songTime = Long.parseLong(songDetails.getSongTime());
        String songTimeFormatted = String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(songTime),
                TimeUnit.MILLISECONDS.toSeconds(songTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(songTime))
        );
        holder.songTimeText.setText(songTimeFormatted);

        holder.setItem(songDetails, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return songDetailsList.size();
    }
}