package com.example.twitchflix;

import java.io.Serializable;
import java.util.Arrays;

public class Movie implements Serializable {
    private String title;
    private int release_year;
    private String[] genre;
    private int duration;
    private String link;
    private String link_pic;

    public Movie(String title, int release_year, int duration, String link, String link_pic) {
        this.title = title;
        this.release_year = release_year;
        this.duration = duration;
        this.link = link;
        this.link_pic = link_pic;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String[] getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Movie: {" +
                "title='" + title + '\'' +
                ", release_year=" + release_year +
                ", genre=" + Arrays.toString(genre) +
                ", duration=" + duration +
                ", link='" + link + '\'' +
                ", link_pic='" + link_pic + '\'' +
                '}';
    }

    public String getLink_pic() { return link_pic; }
}
