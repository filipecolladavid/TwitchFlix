package com.example.twitchflix;

import java.io.Serializable;

public class Stream implements Serializable {

    //Path to add to rtmp url
    private String streamURL;
    //Username that created stream
    private String username;
    //Title of the stream
    private String title;

    public Stream(String streamURL, String username, String title) {
        this.streamURL = streamURL;
        this.username = username;
        this.title = title;
    }

    public String getStreamURL() {
        return streamURL;
    }

    public void setStreamURL(String streamURL) {
        this.streamURL = streamURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
