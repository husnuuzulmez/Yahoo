package com.example.uzulmez.myapplication1;


import java.io.Serializable;

public class FeedItem implements Serializable {
    private String title;
    private String thumbnail;
    private String hq_thumbnail;
    private String ID;


    public String getTitle() {
        return title;
    }
    public String getID() {
        return ID;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public void setID(String Id) {
        this.ID = Id;
    }

    String getThumbnail() {
        return thumbnail;
    }
    String getHQ_Thumbnail() {
        return hq_thumbnail;
    }

    void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    void setHigh_QThumbnail(String High_qthumbnail) {
        this.hq_thumbnail = High_qthumbnail;
    }

}