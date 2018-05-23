package com.example.rajat.internshiptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private String imageURL;
    private ImageView songArtworkImage;

    public DownloadImageTask(String imageURL, ImageView songArtworkImage) {
        this.imageURL = imageURL;
        this.songArtworkImage = songArtworkImage;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        HttpURLConnection httpURLConnection = null;
        Bitmap artwork = null;

        try {
            URL url = new URL(imageURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = httpURLConnection.getInputStream();
            artwork = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return artwork;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (songArtworkImage.getTag() == imageURL) {
            songArtworkImage.setImageBitmap(bitmap);
        }
    }
}