package com.example.rajat.internshiptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SongDetailsActivity extends Activity {
    private static final String SONG_DETAILS = "songDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        TextView songNameText = findViewById(R.id.song_name_text_view);
        TextView kindText = findViewById(R.id.kind_text_view);
        TextView genreText = findViewById(R.id.genre_text_view);
        TextView releaseDateText = findViewById(R.id.release_date_text_view);
        TextView songTimeText = findViewById(R.id.song_time_text_view);
        ImageView songArtworkImage = findViewById(R.id.song_artwork_image_view);
        TextView songPriceText = findViewById(R.id.song_price_text_view);
        TextView collectionNameText = findViewById(R.id.collection_name_text_view);
        TextView collectionPriceText = findViewById(R.id.collection_price_text_view);
        TextView songURLText = findViewById(R.id.song_URL_text_view);
        TextView artistURLText = findViewById(R.id.artist_URL_text_view);

        Intent intent = getIntent();
        SongDetails songDetails = intent.getParcelableExtra(SONG_DETAILS);

        String song = songDetails.getSong();
        String kind = songDetails.getKind();
        String genre = songDetails.getGenre();
        String releaseDate = songDetails.getReleaseDate();
        Long songTime = Long.parseLong(songDetails.getSongTime());
        String imageURL = songDetails.getImageURL();
        String songPrice = "<b>Price:</b> $" + songDetails.getSongPrice();
        String collection = (songDetails.getCollection() != null) ? ("<b>Collection Name:</b> " + songDetails.getCollection()) : "";
        String collectionPrice = "<b>Collection Price:</b> $" + songDetails.getCollectionPrice();
        String songURL = "<b>More Information:</b> " + songDetails.getSongURL();
        String artistURL = (songDetails.getArtistURL() != null) ? ("<b>Artist Information:</b> " + songDetails.getArtistURL()) : "";

        String releaseDateFormatted = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = simpleDateFormat.parse(releaseDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            releaseDateFormatted = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String songTimeFormatted = String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(songTime),
                TimeUnit.MILLISECONDS.toSeconds(songTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(songTime))
        );

        songArtworkImage.setTag(imageURL);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageURL, songArtworkImage);
        downloadImageTask.execute();

        songNameText.setText(song);
        kindText.setText(kind);
        genreText.setText(genre);
        releaseDateText.setText(releaseDateFormatted);
        songTimeText.setText(songTimeFormatted);
        songPriceText.setText(Html.fromHtml(songPrice));
        collectionNameText.setText(Html.fromHtml(collection));
        collectionPriceText.setText(Html.fromHtml(collectionPrice));
        songURLText.setText(Html.fromHtml(songURL));
        artistURLText.setText(Html.fromHtml(artistURL));
    }
}