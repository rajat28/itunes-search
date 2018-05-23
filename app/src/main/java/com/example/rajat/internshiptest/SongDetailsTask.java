package com.example.rajat.internshiptest;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SongDetailsTask extends AsyncTask<Void, Void, ArrayList<SongDetails>> {
    private static final String RESULTS = "results";
    private OnListDisplayListener onListDisplayListener;

    public SongDetailsTask(OnListDisplayListener onListDisplayListener) {
        this.onListDisplayListener = onListDisplayListener;
    }

    @Override
    protected ArrayList<SongDetails> doInBackground(Void... params) {
        HttpURLConnection httpURLConnection = null;
        String data = "";

        try {
            URL url = new URL("https://itunes.apple.com/search?term=Michael+jackson");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            data = readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return parseJSON(data);
    }

    @Override
    protected void onPostExecute(ArrayList<SongDetails> songDetailsList) {
        if (songDetailsList != null) {
            onListDisplayListener.setSongDetailsList(songDetailsList);
        }
    }

    private String readStream(InputStream in) {
        BufferedReader buf = null;
        StringBuilder data = new StringBuilder("");

        try {
            buf = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = buf.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data.toString();
    }

    private ArrayList<SongDetails> parseJSON(String data) {
        SongDetails songDetails;
        ArrayList<SongDetails> songDetailsList = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray(RESULTS);

            for (int i = 0; i < array.length(); i++) {
                JSONObject innerObject = array.getJSONObject(i);
                songDetails = new SongDetails(innerObject);
                songDetailsList.add(songDetails);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songDetailsList;
    }
}