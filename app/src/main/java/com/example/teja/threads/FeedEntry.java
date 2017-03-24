package com.example.teja.threads;

/**
 * Created by teja on 21-03-2017.
 */

public class FeedEntry {
private String name;
    private String artist;
    private String releaseDate;
    private String summary;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "FeedEntry{" +
                "name='" + name + '\'' +
                ", artist=" + artist + '\'' +
                ", releaseDate=" + releaseDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}



