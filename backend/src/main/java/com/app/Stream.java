package com.app;

public class Stream {

    String rtmpPath = "rtmp://127.0.0.1:1935/live/";

    //Path to add to rtmp url
    private String streamURL;
    //Username that created stream
    private String username;
    //Title of the stream
    private String title;

    public Stream( String username, String title) {
        this.username = username;
        this.title = title;
        this.streamURL = rtmpPath+username+"_"+title;
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
