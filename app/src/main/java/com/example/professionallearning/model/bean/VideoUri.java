package com.example.professionallearning.model.bean;

import java.io.Serializable;

public class VideoUri implements Serializable {
    private int id;
    private String title;
    private String uri;
    private String videoFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(String videoFlag) {
        this.videoFlag = videoFlag;
    }
}
