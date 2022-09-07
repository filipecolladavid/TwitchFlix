package com.app;

public class Movies {
    private String title;
    private int release_year;
    private String[] genre;
    private int duration;
    private String link;
    private String link_pic;

    public Movies(String title, int release_year, String[] genre, int duration, String link, String link_pic) {
        this.title = title;
        this.release_year = release_year;
        this.genre = genre;
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

    public String getLink_pic() { return link_pic; }
}
