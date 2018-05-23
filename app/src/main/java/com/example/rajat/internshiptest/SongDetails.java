package com.example.rajat.internshiptest;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SongDetails implements Parcelable {
    private static final String KIND = "kind";
    private static final String COLLECTION_NAME = "collectionName";
    private static final String TRACK_NAME = "trackName";
    private static final String TRACK_VIEW_URL = "trackViewUrl";
    private static final String ARTWORK_URL = "artworkUrl100";
    private static final String COLLECTION_PRICE = "collectionPrice";
    private static final String TRACK_PRICE = "trackPrice";
    private static final String RELEASE_DATE = "releaseDate";
    private static final String TRACK_TIME_MILLIS = "trackTimeMillis";
    private static final String PRIMARY_GENRE_NAME = "primaryGenreName";
    private static final String ARTIST_VIEW_URL = "artistViewUrl";
    private String kind, collection, song, artistURL, songURL, imageURL, collectionPrice,
            songPrice, releaseDate, songTime, genre;

    public SongDetails(JSONObject innerObject) {
        try {
            kind = innerObject.getString(KIND);
            song = innerObject.getString(TRACK_NAME);
            songURL = innerObject.getString(TRACK_VIEW_URL);
            imageURL = innerObject.getString(ARTWORK_URL);
            collectionPrice = innerObject.getString(COLLECTION_PRICE);
            songPrice = innerObject.getString(TRACK_PRICE);
            releaseDate = innerObject.getString(RELEASE_DATE);
            songTime = innerObject.getString(TRACK_TIME_MILLIS);
            genre = innerObject.getString(PRIMARY_GENRE_NAME);

            if (kind.equals("song")) {
                collection = innerObject.getString(COLLECTION_NAME);
                artistURL = innerObject.getString(ARTIST_VIEW_URL);
            } else {
                collection = null;
                artistURL = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getKind() {
        return kind;
    }

    public String getCollection() {
        return collection;
    }

    public String getSong() {
        return song;
    }

    public String getArtistURL() {
        return artistURL;
    }

    public String getSongURL() {
        return songURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public String getSongPrice() {
        return songPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSongTime() {
        return songTime;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(kind);
        out.writeString(collection);
        out.writeString(song);
        out.writeString(artistURL);
        out.writeString(songURL);
        out.writeString(imageURL);
        out.writeString(collectionPrice);
        out.writeString(songPrice);
        out.writeString(releaseDate);
        out.writeString(songTime);
        out.writeString(genre);
    }

    public static final Creator<SongDetails> CREATOR = new Creator<SongDetails>() {
        @Override
        public SongDetails createFromParcel(Parcel in) {
            return new SongDetails(in);
        }

        @Override
        public SongDetails[] newArray(int size) {
            return new SongDetails[size];
        }
    };

    private SongDetails(Parcel in) {
        kind = in.readString();
        collection = in.readString();
        song = in.readString();
        artistURL = in.readString();
        songURL = in.readString();
        imageURL = in.readString();
        collectionPrice = in.readString();
        songPrice = in.readString();
        releaseDate = in.readString();
        songTime = in.readString();
        genre = in.readString();
    }
}
